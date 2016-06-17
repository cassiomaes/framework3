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
package org.demoiselle.internal.producer;

import org.demoiselle.annotation.Name;
import org.demoiselle.internal.proxy.LoggerProxy;
import org.demoiselle.util.CDIUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;
import java.util.logging.Logger;

@Dependent
public class LoggerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Produces a default {@link Logger} instance. If it's possible
	 * to infer the injection point's parent class then this class'es
	 * name will be used to categorize the logger, if not then
	 * the logger won't be categorized.
	 *
	 */
	@Default
	@Produces
	public Logger create(final InjectionPoint ip) {
		String name;

		if (ip != null && ip.getMember() != null) {
			name = ip.getMember().getDeclaringClass().getName();
		} else {
			name = "not.categorized";
		}

		return create(name);
	}

	/*
	 * Produces a {@link Logger} instance categorized by the value
	 * defined by the {@link Name} literal.
	 *
	 */
	@Name
	@Produces
	public Logger createNamed(final InjectionPoint ip) {
		Name nameAnnotation = CDIUtils.getQualifier(Name.class, ip);
		String name = nameAnnotation.value();
		return create(name);
	}

	public static Logger create(String name) {
		return new LoggerProxy(name);
	}
}
