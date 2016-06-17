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
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 *
 * <p>
 * This literal marks a bean to be selected at runtime based on a priority system.
 * The user qualifies the injection point with this literal and then at runtime
 * the CDI engine will circle through all candidate subtypes to be injected
 * that are annotated with {@link Priority}. If there is only one subtype with the
 * highest priority then this one will be selected to be injected.
 * </p>
 *
 * <p>
 * This allows users to plug in libraries with new candidates and have them be selected
 * if their priority values are higher than the default values already present. One example
 * is the {@link org.demoiselle.security.Authorizer} type, the framework has a {@link org.demoiselle.internal.implementation.DefaultAuthorizer}
 * with {@link Priority#L1_PRIORITY the lowest priority} but the user can add libraries with new
 * implementations of {@link org.demoiselle.security.Authorizer} annotated with higher priorities, the code will
 * then automatically select these new implementations with no extra configuration.
 * </p>
 *
 * <p>
 * This annotation must be used with supported types. Usually this involves creating {@link javax.enterprise.inject.Produces} CDI
 * producer methods that will select the correct strategy. To create your own producer
 * methods that support strategy selection, use the utility {@linkplain org.demoiselle.internal.producer.StrategySelector}.
 * </p>
 *
 * @author SERPRO
 */
@Qualifier
@Inherited
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER })
public @interface Strategy {

}
