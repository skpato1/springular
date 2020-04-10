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
	public static final String ANNOTATION_COMPONENT = "@Component\n";
	public static final String ANNOTATION_AUTOWIRED = "@Autowired\n";
	public static final String INJECT_MODEL_MAPPER= "private ConfiguredModelMapper modelMapper;\n";
	public static final String ANNOTATION_SWAGGER_API_RESPONSES = "@ApiResponses";
	public static final String ANNOTATION_SWAGGER_API_RESPONSE = "@ApiResponse";
	public static final String ANNOTATION_SWAGGER_API_OPERATION = "@ApiOperation";
	public static final String ANNOTATION_REQUEST_MAPPING = "@RequestMapping";

	


}
