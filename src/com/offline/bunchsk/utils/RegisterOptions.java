/*
 * Copyright (C) 2016 Kids
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.offline.bunchsk.utils;

import ch.njol.skript.lang.ExpressionType;
import com.offline.bunchsk.effects.EffBroadcastPerm;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 *
 * @author Kids
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterOptions{
    
    String Name() default "BunchSk something";
    String RegType() default "EFFECT";
    Class RegClass() default EffBroadcastPerm.class;
    String[] Versions() default "any";
    String PluginDepend() default "none";
    String[] Syntaxes() default "do something";
    Class ExprClass() default String.class;
    ExpressionType ExprType() default ExpressionType.SIMPLE;
    
}
