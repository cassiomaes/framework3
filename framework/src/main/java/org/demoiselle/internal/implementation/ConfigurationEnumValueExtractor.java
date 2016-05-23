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
package org.demoiselle.internal.implementation;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConversionException;
import org.demoiselle.annotation.Priority;
import org.demoiselle.annotation.literal.NamedQualifier;
import org.demoiselle.configuration.ConfigurationValueExtractor;
import org.demoiselle.util.ResourceBundle;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import java.lang.reflect.Field;

import static org.demoiselle.annotation.Priority.L2_PRIORITY;

@SuppressWarnings("unused")
@Priority(L2_PRIORITY)
@Dependent
public class ConfigurationEnumValueExtractor implements ConfigurationValueExtractor {

	private transient ResourceBundle bundle;

	@Override
	public Object getValue(String prefix, String key, Field field, Configuration configuration) throws Exception {
		String value = configuration.getString(prefix + key);

		if (value != null && !value.trim().equals("")) {
			Object enums[] = field.getType().getEnumConstants();

			for (int i = 0; i < enums.length; i++) {
				if (((Enum<?>) enums[i]).name().equals(value)) {
					return enums[i];
				}
			}
		} else {
			return null;
		}

		throw new ConversionException(getBundle()
				.getString("configuration-not-conversion", value, field.getDeclaringClass().getCanonicalName()));
	}

	@Override
	public boolean isSupported(Field field) {
		return field.getType().isEnum();
	}

	private ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = CDI.current().select(ResourceBundle.class, new NamedQualifier("demoiselle-core-bundle")).get();
		}

		return bundle;
	}

}
