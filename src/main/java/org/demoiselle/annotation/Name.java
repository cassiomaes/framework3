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
package org.demoiselle.annotation;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * This annotation qualifies a bean using a resource name or configuration name. It allows
 * for example to set which file to load for configurations or which setting to load
 * between multiple options or any other situation where the user needs to specify
 * a string based name to customize how the bean works.
 * </p>
 *
 * <p>
 * One such example is with the {@linkplain org.demoiselle.util.ResourceBundle} bean. The {@link Name}
 * annotation can define the name of a properties file to load and populate the bundle with name:value pairs.
 * </p>
 *
 * <p>
 * This annotation is used widely through Demoiselle. Any class that uses this annotation to customize
 * how it works will mention this fact on it's own documentation.
 * </p>
 *
 * @author SERPRO
 */
@Qualifier
@Inherited
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER })
public @interface Name {

	/**
	 * <p>
	 * Specifies a name to access a custom configuration that will change how the annotated bean works.
	 * </p>
	 * <p>
	 * This attribute is nonbinding so you can use the {@link Name} annotation to create {@linkplain javax.enterprise.inject.Produces}
	 * methods or fields and have only one producer that works with all injection points no matter the value of this attribute.
	 * </p>
	 * @return Name of custom settings to personalize how the annotated bean works.
	 */
	@Nonbinding
	String value() default "";

}
