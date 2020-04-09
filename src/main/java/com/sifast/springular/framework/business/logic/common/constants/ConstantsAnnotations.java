package com.sifast.springular.framework.business.logic.common.constants;

public class ConstantsAnnotations {
	
	public static final String ANNOTATION_OVERRIDE = "@Override\n";
	public static final String ANNOTATION_NOT_EMPTY = "@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)\n";
	public static final String ANNOTATION_NOT_NULL = "@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)\n";
	public static final String ANNOTATION_SIZE = "@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)\n";
	public static final String ANNOTATION_REPOSITORY = "@Repository";
	public static final String SUPRESS_WARNINGS = "@SuppressWarnings";
	public static final String ANNOTATION_SERVICE = "@Service";
	public static final String ANNOTATION_TRANSACTIONAL = "@Transactional";
	public static final String ANNOTATION_NO_FLUENT_METHOD = "@noFluentMethod";

}