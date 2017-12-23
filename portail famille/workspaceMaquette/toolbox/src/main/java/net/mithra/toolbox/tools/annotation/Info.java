/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mithra.toolbox.tools.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author SHordoir
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Info {
    String header(); 
}
