/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package org.demoiselle.jsf.internal.implementation;

import org.demoiselle.annotation.Name;
import org.demoiselle.jsf.util.Parameter;
import org.demoiselle.util.Reflections;
import org.demoiselle.util.Strings;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

@Alternative
public class ParameterImpl<T extends Serializable> implements Parameter<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private Class<Object> type;

	private transient Converter converter;

	private T value;

	private final String key;

	private boolean viewScoped = false;

	private boolean requestScoped = false;

	private boolean sessionScoped = false;

	public Converter getConverter() {
		if (converter == null) {
			converter = getConverter(type);
		}

		return converter;
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	@Inject
	public ParameterImpl(InjectionPoint ip) {
		if (ip.getAnnotated().isAnnotationPresent(Name.class)) {
			String key = ip.getAnnotated().getAnnotation(Name.class).value();

			if (!"".equals(key)) {
				this.key = key;
			} else {
				this.key = ip.getMember().getName();
			}
		} else {
			this.key = ip.getMember().getName();
		}

		this.type = Reflections.getGenericTypeArgument(ip.getMember(), 0);

		this.viewScoped = ip.getAnnotated().isAnnotationPresent(ViewScoped.class);
		this.requestScoped = ip.getAnnotated().isAnnotationPresent(RequestScoped.class);
		this.sessionScoped = ip.getAnnotated().isAnnotationPresent(SessionScoped.class);
	}

	public String getKey() {
		return key;
	}

	private boolean isSessionScoped() {
		return sessionScoped;
	}

	private boolean isViewScoped() {
		return viewScoped;
	}

	private boolean isRequestScoped() {
		return requestScoped;
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		T result;
		String parameterValue = getRequest().getParameter(key);

		if (isSessionScoped()) {
			if (parameterValue != null) {
				getRequest().getSession().setAttribute(key, convert(parameterValue, getConverter()));
			}

			result = (T) getRequest().getSession().getAttribute(key);

		} else if (isRequestScoped()) {
			result = (T) convert(parameterValue, getConverter());

		} else if (isViewScoped()) {
			Map<String, Object> viewMap = getViewMap();
			if (parameterValue != null) {
				viewMap.put(key, convert(parameterValue, getConverter()));
			}

			result = (T) viewMap.get(key);

		} else {
			if (value == null) {
				value = (T) convert(parameterValue, getConverter());
			}

			result = value;
		}

		return result;
	}

	@Override
	public void setValue(T value) {
		if (isSessionScoped()) {
			getRequest().getSession().setAttribute(key, value);

		} else if (isRequestScoped()) {
			// FIXME Lançar exceção informando que não é possível setar parâmetros no request.
		} else if (isViewScoped()) {
			Map<String, Object> viewMap = getViewMap();
			viewMap.put(key, value);

		} else {
			this.value = value;
		}
	}

	private Converter getConverter(Class<?> targetClass) {
		Converter result;

		try {
			Application application = FacesContext.getCurrentInstance().getApplication();
			result = application.createConverter(targetClass);

		} catch (Exception e) {
			result = null;
		}

		return result;
	}

	private Object convert(final String value, final Converter converter) {
		Object result = null;

		if (!Strings.isEmpty(value)) {
			if (converter != null) {
				result = converter
						.getAsObject(FacesContext.getCurrentInstance(), FacesContext.getCurrentInstance().getViewRoot(),
								value);
			} else {
				result = value;
			}
		}

		return result;
	}

	private static Map<String, Object> getViewMap() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		return viewRoot.getViewMap(true);
	}
}
