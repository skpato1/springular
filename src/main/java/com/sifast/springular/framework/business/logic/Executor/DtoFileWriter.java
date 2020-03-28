package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class DtoFileWriter {

	public void generateSuperFilesInEachFolderDTO(BuisnessLogicEntity entity, Project project) {
		writeRelationshipsAttributesInDto(entity);
		project.getEntities().stream().forEach(ent -> {
			try {
				String fileDto = ent.getNameEntity().concat("Dto");
				FileWriter myWriter = writeImportsAndStructureOfClassInDto(ent);
				writeAttributesWithValidationAnnotationInDto(ent, myWriter);
				writeGettersAndSettersForDto(ent, myWriter);
				writeToStringMethodInDto(ent, fileDto, myWriter);
				closeAccoladeAndFile(myWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void writeRelationshipsAttributesInDto(BuisnessLogicEntity entity) {
		List<String> relationshipsNamesOneToMany = entity.getRelationshipsSlave().stream().filter(
				relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
				.map(relationship -> relationship.getMasterEntity().getNameEntity()).collect(Collectors.toList());

		List<String> relationshipsNamesOneToOne = entity.getRelationshipsMaster().stream().filter(
				relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
				.map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

		List<String> relationshipsNamesManyToMany = entity.getRelationshipsMaster().stream()
				.filter(relationship -> relationship.getTypeRelationship().name()
						.equals(RelationshipTypeEnum.ManyToMany.name()))
				.map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

		List<Attribute> attributes = entity.getAttributes();
		for (int i = 0; i < relationshipsNamesOneToMany.size(); i++) {

			Attribute attOneToMany = new Attribute();
			attOneToMany.setNameAttribute(relationshipsNamesOneToMany.get(i).toLowerCase().concat("Id"));
			attOneToMany.setTypeAttribute(AttributesTypeEnum.Long);
			attributes.add(attOneToMany);

		}
		for (int j = 0; j < relationshipsNamesOneToMany.size(); j++) {

			Attribute attOneToOne = new Attribute();
			attOneToOne.setNameAttribute(relationshipsNamesOneToOne.get(j).toLowerCase().concat("Id"));
			attOneToOne.setTypeAttribute(AttributesTypeEnum.Long);
			attributes.add(attOneToOne);
		}

		for (int z = 0; z < relationshipsNamesManyToMany.size(); z++) {

			Attribute attManyToMany = new Attribute();
			attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("Id"));
			attManyToMany.setTypeAttribute(AttributesTypeEnum.Set);
			attributes.add(attManyToMany);
		}
		entity.setAttributes(attributes);
	}

	private void writeToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter)
			throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.ANNOTATION_OVERRIDE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.TO_STRING_METHOD);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.DECLARATION_STRING_BUILDER);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(fileDto).concat(" ").concat(Constants.CROCHEE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

		ent.getAttributes().stream().forEach(attribute -> {
			try {
				if (!attribute.getTypeAttribute().equals(AttributesTypeEnum.Set)
						&& !attribute.getNameAttribute().contains("Id")) {
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.PATTERN_TABULATION);
					List<Attribute> attributes = ent.getAttributes();
					if (attributes.get(0).getNameAttribute() == attribute.getNameAttribute()) {
						myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.DOUBLE_COTE).concat(attribute.getNameAttribute())
								.concat(Constants.EGALE).concat(Constants.DOUBLE_COTE)
								.concat(Constants.PARENTHESE_FERMANTE)
								.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					} else {
						myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
								.concat(attribute.getNameAttribute()).concat(Constants.EGALE)
								.concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
								.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					}
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
							.concat(attribute.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE)
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.RETURN_BUILDER_TO_STRING);
		myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));

		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
	}

	private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE);
		myWriter.close();
	}

	private FileWriter writeImportsAndStructureOfClassInDto(BuisnessLogicEntity ent) throws IOException {
		File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
				.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(ent.getNameEntity())
				.concat("Dto.java"));
		String fileDto = ent.getNameEntity().concat("Dto");
		FileWriter myWriter = new FileWriter(file);
		myWriter.write("");
		myWriter.write(
				Constants.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		ent.getAttributes().stream().forEach(attribute -> {
			if (attribute.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
				try {
					myWriter.write(Constants.IMPORT_SET);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		myWriter.write(Constants.IMPORT_DTO_VALIDATION_NOT_EMPTY);
		myWriter.write(Constants.IMPORT_DTO_VALIDATION_NOT_NULL);
		myWriter.write(Constants.IMPORT_DTO_VALIDATION_SIZE);
		myWriter.write(Constants.IMPORT_DTO_API_MESSAGE);
		myWriter.write(Constants.IMPORT_DTO_CONSTANTS);
		myWriter.write(Constants.IMPORT_DTO_IWebServicesValidators);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
		return myWriter;
	}

	private void writeAttributesWithValidationAnnotationInDto(BuisnessLogicEntity ent, FileWriter myWriter) {

		ent.getAttributes().stream().forEach(attribute -> {
			try {
				if (!attribute.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name())) {
					if (!attribute.getNameAttribute().contains("Id")) {
						myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ANNOTATION_NOT_NULL));
					}
					if (attribute.getTypeAttribute().name().equals("String")) {
						myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ANNOTATION_NOT_EMPTY));
						myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ANNOTATION_SIZE));
					}
					myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE)
							.concat(attribute.getTypeAttribute().name().concat(" "))
							.concat(attribute.getNameAttribute())
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				} else {
					myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE)
							.concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
							.concat(Constants.LONG).concat(" ").concat(attribute.getNameAttribute())
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
			} catch (Exception e) {
			}

		});

	}

	private void writeGettersAndSettersForDto(BuisnessLogicEntity ent, FileWriter myWriter) {
		ent.getAttributes().stream().forEach(attributeForGetterAndSetter -> {
			String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
			String getterAttribute = firstLetter.toUpperCase()
					.concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
			try {
				myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC)
						.concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(" ").concat("get")
						.concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
						.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION)
								.concat(Constants.RETURN))
						.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

				myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID)
						.concat("set").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
						.concat(attributeForGetterAndSetter.getTypeAttribute().name().concat(" ")
								.concat(attributeForGetterAndSetter.getNameAttribute()))
						.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
								.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE)
								.concat(attributeForGetterAndSetter.getNameAttribute()))
						.concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

			} catch (Exception e) {
				// TODO: handle exception
			}
		});
	}

	public void generateCreateFilesInEachFolderDTO(Project project) {
		
		
		project.getEntities().stream().forEach(ent -> {
			try {
				File fileCreate = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
						.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat("Create")
						.concat(ent.getNameEntity().concat("Dto.java")));
				FileWriter myWriter = writeCreateImportsAndStructureOfClassInDto(ent);
				closeAccoladeAndFile(myWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	private FileWriter writeCreateImportsAndStructureOfClassInDto(BuisnessLogicEntity ent) throws IOException {
		String fileDto = "Create".concat(ent.getNameEntity()).concat("Dto");
		File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
				.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH)
				.concat(fileDto)
				.concat(".java"));
				
		
		FileWriter myWriter = new FileWriter(file);
		myWriter.write("");
		myWriter.write(
				Constants.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		ent.getAttributes().stream().forEach(attribute -> {
			if (attribute.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
				try {
					myWriter.write(Constants.IMPORT_SET);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PUBLIC_CLASS
				.concat(fileDto)
				.concat(Constants.EXTENDS)
				.concat(ent.getNameEntity()).concat("Dto")
				.concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
		return myWriter;
	}

	public void generateViewFilesInEachFolderDTO(BuisnessLogicEntity entity,Project project) {
		writeViewRelationshipsAttributesInDto(entity);	
		project.getEntities().stream().forEach(ent -> {
			try {
				File fileView = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
						.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat("View")
						.concat(ent.getNameEntity().concat("Dto.java")));
				String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
				FileWriter myWriter = writeViewImportsAndStructureOfClassInDto(ent);
				writeViewAttributesInDto(ent, myWriter);
				writeViewRelationshipAttributeFromAttribute(ent,myWriter);
				writeViewGettersAndSettersForDto(ent, myWriter);
				writeViewGettersAndSettersForRelationshipAttributes(ent,myWriter);
				writeViewToStringMethodInDto(ent, fileDto, myWriter);
				closeAccoladeAndFile(myWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	private void writeViewGettersAndSettersForRelationshipAttributes(BuisnessLogicEntity ent, FileWriter myWriter) {
			ent.getAttributes().stream().forEach(attributeForGetterAndSetter->{
				try {
					if(attributeForGetterAndSetter.getTypeAttribute().equals(AttributesTypeEnum.Set))
					{	
						String firstLetterDto = attributeForGetterAndSetter.getNameAttribute().charAt(0)+"";
						String viewDto=attributeForGetterAndSetter.getNameAttribute().substring(1,attributeForGetterAndSetter.getNameAttribute().length()-1);
						String typeSetDto= "View".concat(firstLetterDto.toUpperCase()).concat(viewDto).concat("Dto");
						String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
						String getterAttribute = firstLetter.toUpperCase()
								.concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
						myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC)
								.concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(" ").concat("get")
								.concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION)
										.concat(Constants.RETURN))
								.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

						myWriter.write(Constants.PATTERN_TABULATION
								.concat(Constants.PUBLIC)
								.concat(Constants.VOID)
								.concat("set")
								.concat(getterAttribute)
								.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(attributeForGetterAndSetter.getTypeAttribute().name())
								.concat(Constants.INFERIEUR)
								.concat(typeSetDto)
								.concat(Constants.SUPERIEUR)
								.concat(" ")
								.concat(attributeForGetterAndSetter.getNameAttribute())
								.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
										.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE)
										.concat(attributeForGetterAndSetter.getNameAttribute()))
								.concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
					}
					if(attributeForGetterAndSetter.getTypeAttribute().equals(AttributesTypeEnum.List))
					{
						String firstLetterDto = attributeForGetterAndSetter.getNameAttribute().charAt(0)+"";
						String viewDto=attributeForGetterAndSetter.getNameAttribute().substring(1);
						String typeSetDto= "View".concat(firstLetterDto.toUpperCase()).concat(viewDto).concat("Dto");
						String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
						String getterAttribute = firstLetter.toUpperCase()
								.concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
						myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC)
								.concat(typeSetDto).concat(" ").concat("get")
								.concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION)
										.concat(Constants.RETURN))
								.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

						myWriter.write(Constants.PATTERN_TABULATION
								.concat(Constants.PUBLIC)
								.concat(Constants.VOID)
								.concat("set")
								.concat(getterAttribute)
								.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(typeSetDto)
								.concat(" ")
								.concat(attributeForGetterAndSetter.getNameAttribute())
								.concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
										.concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE)
										.concat(attributeForGetterAndSetter.getNameAttribute()))
								.concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
						myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
					
				
	}

	private void writeViewRelationshipAttributeFromAttribute(BuisnessLogicEntity ent, FileWriter myWriter) {
		ent.getAttributes().stream().forEach(attribute -> {
			try {
				String firstLetter = attribute.getNameAttribute().charAt(0)+"";
				String view= firstLetter.toUpperCase()+attribute.getNameAttribute().substring(1);
					if(attribute.getTypeAttribute().equals(AttributesTypeEnum.List))
					{
					myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE)
							.concat("View")
							.concat(view)
							.concat("Dto")
							.concat(" ")
							.concat(attribute.getNameAttribute())
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					}
					if(attribute.getTypeAttribute().equals(AttributesTypeEnum.Set))
					{
					myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE)
							.concat(AttributesTypeEnum.Set.name())
							.concat(Constants.INFERIEUR)
							.concat("View")
							.concat(view)
							.concat("Dto")
							.concat(Constants.SUPERIEUR)
							.concat(" ")
							.concat(attribute.getNameAttribute())
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
			} catch (Exception e) {
			}

		});
		
	}

	private void writeViewRelationshipsAttributesInDto(BuisnessLogicEntity entity) {
		
		List<String> relationshipsNamesOneToMany = entity.getRelationshipsSlave().stream().filter(
				relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
				.map(relationship -> relationship.getMasterEntity().getNameEntity()).collect(Collectors.toList());

		List<String> relationshipsNamesOneToOne = entity.getRelationshipsMaster().stream().filter(
				relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
				.map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

		List<String> relationshipsNamesManyToMany = entity.getRelationshipsMaster().stream()
				.filter(relationship -> relationship.getTypeRelationship().name()
						.equals(RelationshipTypeEnum.ManyToMany.name()))
				.map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

		List<Attribute> attributes = entity.getAttributes();
		for (int i = 0; i < relationshipsNamesOneToMany.size(); i++) {

			Attribute attOneToMany = new Attribute();
			attOneToMany.setNameAttribute(relationshipsNamesOneToMany.get(i).toLowerCase());
			attOneToMany.setTypeAttribute(AttributesTypeEnum.List);
			attributes.add(attOneToMany);

		}
		
		for (int i = 0; i < relationshipsNamesOneToOne.size(); i++) {

			Attribute attOneToOne = new Attribute();
			attOneToOne.setNameAttribute(relationshipsNamesOneToOne.get(i).toLowerCase());
			attOneToOne.setTypeAttribute(AttributesTypeEnum.List);
			attributes.add(attOneToOne);

		}
		

		for (int z = 0; z < relationshipsNamesManyToMany.size(); z++) {

			Attribute attManyToMany = new Attribute();
			attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("s"));
			attManyToMany.setTypeAttribute(AttributesTypeEnum.Set);
			attributes.add(attManyToMany);
		}
		entity.setAttributes(attributes);
		
	}

	private void writeViewToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter) throws IOException {
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.ANNOTATION_OVERRIDE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.TO_STRING_METHOD);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.DECLARATION_STRING_BUILDER);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(fileDto).concat(" ").concat(Constants.CROCHEE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

			try {
				
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.PATTERN_TABULATION);
					List<Attribute> attributes = ent.getAttributes();
					if (attributes.get(0).getNameAttribute() != Constants.ID_MINUS) {
						myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.DOUBLE_COTE).concat(Constants.ID_MINUS)
								.concat(Constants.EGALE).concat(Constants.DOUBLE_COTE)
								.concat(Constants.PARENTHESE_FERMANTE)
								.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					} else {
						myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
								.concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
								.concat(Constants.ID_MINUS).concat(Constants.EGALE)
								.concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
								.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
					}
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.PATTERN_TABULATION);
					myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE)
							.concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
							.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
				.concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.RETURN_BUILDER_TO_STRING);
		myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));

		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
	}

	private void writeViewGettersAndSettersForDto(BuisnessLogicEntity ent, FileWriter myWriter) {
		
			try {
				myWriter.write(Constants.PATTERN_TABULATION
						.concat(Constants.PUBLIC)
						.concat(AttributesTypeEnum.Long.name())
						.concat(" ")
						.concat("get")
						.concat(Constants.ID)
						.concat(Constants.PARENTHESE_OUVRANTE)
						.concat(Constants.PARENTHESE_FERMANTE)
						.concat(" ")
						.concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.RETURN)
						.concat(Constants.ID_MINUS)
						.concat(Constants.PATTERN_POINT_VIRGULE)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.ACCOLADE_FERMANTE));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

				myWriter.write(Constants.PATTERN_TABULATION
						.concat(Constants.PUBLIC)
						.concat(Constants.VOID)
						.concat("set")
						.concat(Constants.ID)
						.concat(Constants.PARENTHESE_OUVRANTE)
						.concat(AttributesTypeEnum.Long.name())
						.concat(" ")
						.concat(Constants.ID_MINUS)
						.concat(Constants.PARENTHESE_FERMANTE)
						.concat(" ")
						.concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.THIS)
						.concat(Constants.ID_MINUS)
						.concat(Constants.EGALE)
						.concat(Constants.ID_MINUS)
						.concat(Constants.PATTERN_POINT_VIRGULE)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.PATTERN_TABULATION)
						.concat(Constants.ACCOLADE_FERMANTE));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

			} catch (Exception e) {
				// TODO: handle exception
			}
		
	}

	private void writeViewAttributesInDto(BuisnessLogicEntity ent, FileWriter myWriter) {
		
		
				try {
					myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE)
							.concat(AttributesTypeEnum.Long.name()).concat(" ")
							.concat("id")
							.concat(Constants.PATTERN_RETOUR_LIGNE));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			
	}

	private FileWriter writeViewImportsAndStructureOfClassInDto(BuisnessLogicEntity ent) throws IOException {
		String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
		File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
				.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH)
				.concat(fileDto)
				.concat(".java"));
				
		
		FileWriter myWriter = new FileWriter(file);
		myWriter.write("");
		myWriter.write(
				Constants.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		ent.getAttributes().stream().forEach(attribute -> {
			if (attribute.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
				try {
					myWriter.write(Constants.IMPORT_SET);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PUBLIC_CLASS
				.concat(fileDto)
				.concat(Constants.EXTENDS)
				.concat(ent.getNameEntity()).concat("Dto")
				.concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
		return myWriter;
	}


}
