package net.mithra.toolbox.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.mithra.toolbox.bean.impl.ExcelServiceImpl.FormatType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {
	/**
	 * (Required)
	 * @return
	 */
	public int position() default 0;
	/** (Required)
	 * 
	 * @return
	 */
	public FormatType type() default FormatType.TEXT;
}
