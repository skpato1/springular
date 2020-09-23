package com.sifast.springular.framework.business.logic.executor;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DtoFileWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DtoFileWriter.class);

    public void generateSuperFilesInEachFolderDTO(Project project) {
        LOGGER.debug("generateSuperFilesInEachFolderDTO");
        project.getEntities().stream().forEach(ent -> {
            try {
                List<Attribute> attributes = writeRelationshipsAttributesInDto(ent);
                String fileDto = ent.getNameEntity().concat("Dto");
                FileWriter myWriter = writeImportsAndStructureOfClassInDto(project, ent, attributes);
                writeAttributesWithValidationAnnotationInDto(ent, myWriter, attributes);
                writeGettersAndSettersForDto(myWriter, attributes, ent);
                writeToStringMethodInDto(ent, fileDto, myWriter);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private List<Attribute> writeRelationshipsAttributesInDto(BuisnessLogicEntity entity) {
        LOGGER.debug("writeRelationshipsAttributesInDto");
        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < entity.getAttributes().size(); i++) {
            attributes.add(entity.getAttributes().get(i));
        }

        writeOneToManyRelationships(entity, attributes);

        writeOneToOneRelationships(entity, attributes);

        writeManyToManyRelationships(entity, attributes);

        writeOneToManyParentRelationsShips(entity, attributes);

        return attributes;
    }

    private void writeOneToManyParentRelationsShips(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeOneToManyParentRelationsShips");
        List<String> relationshipsNamesOneToManyParent = entity.getRelationshipsParent().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getChildEntity().getNameEntity()).collect(Collectors.toList());

        for (int i = 0; i < relationshipsNamesOneToManyParent.size(); i++) {
            if (Boolean.TRUE.equals(entity.getCreateListIdsIfChild())) {
                Attribute attOneToManyParent = new Attribute();
                attOneToManyParent.setNameAttribute(relationshipsNamesOneToManyParent.get(i).toLowerCase().concat("Ids"));
                attOneToManyParent.setTypeAttribute(AttributesTypeEnum.Set);
                attributes.add(attOneToManyParent);
            }
        }
    }

    private void writeManyToManyRelationships(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeManyToManyRelationships");
        List<String> relationshipsNamesManyToMany = entity.getRelationshipsParent().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.ManyToMany.name()))
                .map(relationship -> relationship.getChildEntity().getNameEntity()).collect(Collectors.toList());
        for (int z = 0; z < relationshipsNamesManyToMany.size(); z++) {

            Attribute attManyToMany = new Attribute();
            if (Boolean.TRUE.equals(entity.getCreateListDtosIfChild())) {
                attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("s"));
            } else {
                attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("Ids"));
            }
            attManyToMany.setTypeAttribute(AttributesTypeEnum.Set);
            attributes.add(attManyToMany);
        }
    }

    private void writeOneToOneRelationships(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeOneToOneRelationships");
        List<String> relationshipsNamesOneToOne = entity.getRelationshipsParent().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
                .map(relationship -> relationship.getChildEntity().getNameEntity()).collect(Collectors.toList());

        for (int j = 0; j < relationshipsNamesOneToOne.size(); j++) {
            Attribute attOneToOne = new Attribute();
            attOneToOne.setNameAttribute(relationshipsNamesOneToOne.get(j).toLowerCase().concat("Id"));
            attOneToOne.setTypeAttribute(AttributesTypeEnum.Long);
            attributes.add(attOneToOne);
        }
    }

    private void writeOneToManyRelationships(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeOneToManyRelationships");
        List<String> relationshipsNamesOneToMany = entity.getRelationshipsChild().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getParentEntity().getNameEntity()).collect(Collectors.toList());

        for (int i = 0; i < relationshipsNamesOneToMany.size(); i++) {

            Attribute attOneToMany = new Attribute();
            attOneToMany.setNameAttribute(relationshipsNamesOneToMany.get(i).toLowerCase().concat("Id"));
            attOneToMany.setTypeAttribute(AttributesTypeEnum.Long);
            attributes.add(attOneToMany);

        }
    }

    private void writeToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter) throws IOException {
        LOGGER.debug("writeToStringMethodInDto");
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_OVERRIDE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.TO_STRING_METHOD);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.DECLARATION_STRING_BUILDER);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(fileDto).concat(" ").concat(Constants.CROCHEE_OUVRANTE)
                .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        ent.getAttributes().stream().forEach(attribute -> {
            try {
                if (!attribute.getTypeAttribute().equals(AttributesTypeEnum.Set) && !attribute.getNameAttribute().contains("Id")) {
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.PATTERN_TABULATION);
                    List<Attribute> attributes = ent.getAttributes();
                    if (attribute.getNameAttribute().equals(attributes.get(0).getNameAttribute())) {
                        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(attribute.getNameAttribute())
                                .concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                    } else {
                        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
                                .concat(attribute.getNameAttribute()).concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                    }
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(attribute.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RETURN_BUILDER_TO_STRING);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        LOGGER.debug("closeAccoladeAndFile");
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();
    }

    private FileWriter writeImportsAndStructureOfClassInDto(Project project, BuisnessLogicEntity ent, List<Attribute> attributes) throws IOException {
        LOGGER.debug("writeImportsAndStructureOfClassInDto");
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(ent.getNameEntity()).concat("Dto.java"));
        String fileDto = ent.getNameEntity().concat("Dto");
        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_SET);

        ent.getAttributes().stream().forEach(attribute -> {
            if (attribute.getTypeAttribute().toString().equals(AttributesTypeEnum.LocalDate.toString())) {
                try {
                    myWriter.write(ConstantsImportPackage.IMPORT_LOCAL_DATE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        attributes.stream().forEach(attribute -> {
            if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild()) && attribute.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name())) {
                try {
                    String first = firstLetterUpperCase(attribute);
                    first = first.substring(0, first.length() - 1);
                    String dto = first.concat("Dto");
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute().substring(0, attribute.getNameAttribute().length() - 1))
                            .concat(Constants.POINT).concat(dto).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_EMPTY);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_NULL);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_SIZE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_CONSTANTS);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_I_WEB_SERVICES_VALIDATORS);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

    private void writeAttributesWithValidationAnnotationInDto(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributes) {
        LOGGER.debug("writeAttributesWithValidationAnnotationInDto");
        Attribute attributeId = new Attribute();
        attributeId.setNameAttribute("id");
        attributeId.setTypeAttribute(AttributesTypeEnum.Long);
        attributes.add(attributeId);
        attributes.stream().forEach(attribute -> {
            try {
                if (!attribute.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name()) && !attribute.getTypeAttribute().name().contains(AttributesTypeEnum.List.name())) {
                    if (!attribute.getNameAttribute().contains("Id")) {
                        myWriter.write(Constants.PATTERN_TABULATION.concat(ConstantsAnnotations.ANNOTATION_NOT_NULL));
                    }
                    if (attribute.getTypeAttribute().name().equals("String")) {
                        myWriter.write(Constants.PATTERN_TABULATION.concat(ConstantsAnnotations.ANNOTATION_NOT_EMPTY));
                        myWriter.write(Constants.PATTERN_TABULATION.concat(ConstantsAnnotations.ANNOTATION_SIZE));
                    }
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name().concat(" "))
                            .concat(attribute.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                } else {
                    String firstLetter = "" + attribute.getNameAttribute().charAt(0);
                    String attributeDto = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length() - 1);
                    String attributeDtoEndsWithDto = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length() - 4);

                    if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild()) && attribute.getNameAttribute().endsWith("Dto")) {
                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(attributeDtoEndsWithDto).concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attribute.getNameAttribute())
                                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                    }
                    if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild())) {
                        if (!attribute.getNameAttribute().endsWith("Dto")) {
                            myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                    .concat(attributeDto).concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attribute.getNameAttribute())
                                    .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                        }
                    } else {

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(Constants.LONG).concat(" ").concat(attribute.getNameAttribute())

                                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                    }

                }
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private void writeGettersAndSettersForDto(FileWriter myWriter, List<Attribute> attributes, BuisnessLogicEntity ent) {
        LOGGER.debug("writeGettersAndSettersForDto");
        attributes.stream().forEach(attributeForGetterAndSetter -> {
            String firstLetter = null;
            String getterAttribute = null;
            if (!attributeForGetterAndSetter.getNameAttribute().isEmpty()) {
                firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
                getterAttribute = firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
            }
            try {
                if (!attributeForGetterAndSetter.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name())
                        && !attributeForGetterAndSetter.getTypeAttribute().name().contains(AttributesTypeEnum.List.name())) {

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(" ").concat("get")
                            .concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                            .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                            .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute)
                            .concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(attributeForGetterAndSetter.getTypeAttribute().name().concat(" ").concat(attributeForGetterAndSetter.getNameAttribute()))
                            .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS).concat(attributeForGetterAndSetter.getNameAttribute())
                                    .concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                            .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                } else {

                    if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild()) && attributeForGetterAndSetter.getNameAttribute().endsWith("Dto")) {
                        String attributeDtoEndsWithDto = firstLetter.toUpperCase()
                                + attributeForGetterAndSetter.getNameAttribute().substring(1, attributeForGetterAndSetter.getNameAttribute().length() - 4).concat("Dto");

                        String typeSetDto = viewFileDto(attributeForGetterAndSetter);
                        typeSetDto = typeSetDto.substring(4);
                        String getterrAttribute = firstLetterUpperCase(attributeForGetterAndSetter);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name())
                                .concat(Constants.INFERIEUR).concat(attributeDtoEndsWithDto).concat(Constants.SUPERIEUR).concat(" ").concat("get").concat(getterrAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterrAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(attributeDtoEndsWithDto).concat(Constants.SUPERIEUR).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute())
                                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
                                        .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));

                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                    }

                    if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild()) && !attributeForGetterAndSetter.getNameAttribute().endsWith("Dto")) {
                        String typeSetDto = viewFileDto(attributeForGetterAndSetter);
                        typeSetDto = typeSetDto.substring(4);
                        String getterrAttribute = firstLetterUpperCase(attributeForGetterAndSetter);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name())
                                .concat(Constants.INFERIEUR).concat(typeSetDto).concat(Constants.SUPERIEUR).concat(" ").concat("get").concat(getterrAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterrAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR).concat(typeSetDto)
                                .concat(Constants.SUPERIEUR).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
                                        .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));

                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                    }
                    if (Boolean.TRUE.equals(ent.getCreateListIdsIfChild())) {

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name())
                                .concat(Constants.INFERIEUR).concat(Constants.LONG).concat(" ").concat("get").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
                                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(Constants.LONG).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
                                        .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                    }

                }
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void generateViewFilesInEachFolderDTO(Project project) {
        LOGGER.debug("generateViewFilesInEachFolderDTO");
        project.getEntities().stream().forEach(ent -> {
            try {
                List<Attribute> attributes = writeRelationshipsAttributesInDto(ent);
                List<Attribute> attributesView = writeViewRelationshipsAttributesInDto(ent);
                for (int i = 0; i < attributesView.size(); i++) {
                    for (int j = 0; j < attributes.size(); j++) {
                        if (attributes.get(j).getNameAttribute().equals(attributesView.get(i).getNameAttribute())
                                && attributes.get(j).getTypeAttribute().equals(AttributesTypeEnum.Set)) {
                            attributes.get(j).setNameAttribute(attributes.get(j).getNameAttribute().concat("Dto"));
                        }
                    }

                }
                String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
                FileWriter myWriter = writeViewImportsAndStructureOfClassInDto(project, ent, attributesView);
                writeViewAttributes(ent, attributesView, myWriter);
                writeViewGettersAndSetters(ent, attributesView, myWriter);
                writeViewToStringMethodInDto(ent, fileDto, myWriter);
                closeAccoladeAndFile(myWriter);
                writeViewImportsAfterAddRelationshipsAttribute(ent, project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void writeViewImportsAfterAddRelationshipsAttribute(BuisnessLogicEntity ent, Project project) throws IOException {
        LOGGER.debug("writeViewImportsAfterAddRelationshipsAttribute");
        String child = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
        String nameFolderToWriteIn = ent.getNameEntity().toLowerCase();
        String namefileToWriteIn = "View".concat(ent.getNameEntity()).concat("Dto");
        if (child != null) {
            String viewChildDto = "View".concat(child).concat("Dto");
            File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                    .concat(nameFolderToWriteIn).concat(Constants.PATTERN_SLASH).concat(namefileToWriteIn).concat(".java"));
            String fileContext = FileUtils.readFileToString(file);
            fileContext = fileContext.replaceAll(ConstantsImportPackage.IMPORT_DTO_I_WEB_SERVICES_VALIDATORS,
                    ConstantsImportPackage.IMPORT_DTO_I_WEB_SERVICES_VALIDATORS.concat(ConstantsImportPackage.IMPORT_DTO.concat(child.toLowerCase()).concat(Constants.POINT)
                            .concat(viewChildDto).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)));
            FileUtils.write(file, fileContext);
        }

    }

    private void writeViewGettersAndSetters(BuisnessLogicEntity ent, List<Attribute> attributesView, FileWriter myWriter) {
        LOGGER.debug("writeViewGettersAndSetters");
        writeViewGettersAndSettersForRelationshipAttributes(ent, myWriter, attributesView);
        writeGettersAndSettersForDto(myWriter, attributesView, ent);
    }

    private void writeViewAttributes(BuisnessLogicEntity ent, List<Attribute> attributesView, FileWriter myWriter) throws IOException {
        LOGGER.debug("writeViewAttributes");
        writeViewRelationshipAttributeFromAttribute(ent, myWriter, attributesView);
        writeAttributesWithValidationAnnotationInDto(ent, myWriter, attributesView);
    }

    private void writeViewGettersAndSettersForRelationshipAttributes(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributes) {
        LOGGER.debug("writeViewGettersAndSettersForRelationshipAttributes");
        attributes.stream().forEach(attributeForGetterAndSetter -> {
            try {
                if (attributeForGetterAndSetter.getTypeAttribute() != null) {
                    if (attributeForGetterAndSetter.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
                        String typeSetDto = viewFileDto(attributeForGetterAndSetter);
                        String getterAttribute = firstLetterUpperCase(attributeForGetterAndSetter);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name())
                                .concat(Constants.INFERIEUR).concat(typeSetDto).concat(Constants.SUPERIEUR).concat(" ").concat("get").concat(getterAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR).concat(typeSetDto)
                                .concat(Constants.SUPERIEUR).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
                                        .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));

                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                    }
                    if (attributeForGetterAndSetter.getTypeAttribute().equals(AttributesTypeEnum.List)) {
                        String firstLetterDto = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
                        String viewDto = attributeForGetterAndSetter.getNameAttribute().substring(1);
                        String typeSetDto = "View".concat(firstLetterDto.toUpperCase()).concat(viewDto).concat("Dto");
                        String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
                        String getterAttribute = firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(typeSetDto).concat(" ").concat("get").concat(getterAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute)
                                .concat(Constants.PARENTHESE_OUVRANTE).concat(typeSetDto).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute())
                                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS)
                                        .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                                .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));

                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                    }
                } else {
                    String child = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
                    String viewChildDto = "View".concat(child).concat("Dto");
                    String getterAttribute = firstLetterUpperCase(attributeForGetterAndSetter);
                    getterNewRelationShipAttributeForViewDto(myWriter, attributeForGetterAndSetter, viewChildDto, getterAttribute);
                    setterNewRelationShipAttributeForViewDto(myWriter, attributeForGetterAndSetter, viewChildDto, getterAttribute);
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void setterNewRelationShipAttributeForViewDto(FileWriter myWriter, Attribute attributeForGetterAndSetter, String viewChildDto, String getterAttribute)
            throws IOException {
        LOGGER.debug("setterNewRelationShipAttributeForViewDto");
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(AttributesTypeEnum.Set.toString()).concat(Constants.INFERIEUR).concat(viewChildDto).concat(Constants.SUPERIEUR).concat(" ")
                .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS).concat(attributeForGetterAndSetter.getNameAttribute())
                        .concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
    }

    private void getterNewRelationShipAttributeForViewDto(FileWriter myWriter, Attribute attributeForGetterAndSetter, String viewChildDto, String getterAttribute)
            throws IOException {
        LOGGER.debug("getterNewRelationShipAttributeForViewDto");
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(AttributesTypeEnum.Set.toString()).concat(Constants.INFERIEUR).concat(viewChildDto)
                .concat(Constants.SUPERIEUR).concat(" ").concat("get").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                .concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN)).concat(attributeForGetterAndSetter.getNameAttribute())
                .concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private String firstLetterUpperCase(Attribute attributeForGetterAndSetter) {
        LOGGER.debug("firstLetterUpperCase");
        String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
        return firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
    }

    private String viewFileDto(Attribute attributeForGetterAndSetter) {
        LOGGER.debug("viewFileDto");
        String firstLetterDto = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
        String viewDto = attributeForGetterAndSetter.getNameAttribute().substring(1, attributeForGetterAndSetter.getNameAttribute().length() - 1);
        return "View".concat(firstLetterDto.toUpperCase()).concat(viewDto).concat("Dto");
    }

    private void writeViewRelationshipAttributeFromAttribute(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributesView) throws IOException {
        LOGGER.debug("writeViewRelationshipAttributeFromAttribute");
        attributesView.stream().forEach(attributeView -> {
            try {
                String firstLetter = attributeView.getNameAttribute().charAt(0) + "";
                String view = firstLetter.toUpperCase() + attributeView.getNameAttribute().substring(1, attributeView.getNameAttribute().length());

                if (attributeView.getTypeAttribute().equals(AttributesTypeEnum.List) && !attributeView.getNameAttribute().endsWith("s")) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat("View").concat(view).concat("Dto").concat(" ")
                            .concat(attributeView.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }

                if (attributeView.getNameAttribute().endsWith("s")) {
                    view = view.substring(0, view.length() - 1);
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(AttributesTypeEnum.Set.name()).concat(Constants.INFERIEUR).concat("View")
                            .concat(view).concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attributeView.getNameAttribute())
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }

                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        String nameChildEntity = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
        if (nameChildEntity != null) {
            String firstLetterView = nameChildEntity.charAt(0) + "";
            String attributeToLowerCase = firstLetterView.toLowerCase() + nameChildEntity.substring(1, nameChildEntity.length()).toLowerCase();
            String attributeSelaveEntityName = attributeToLowerCase + "s";
            myWriter.write(
                    Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(AttributesTypeEnum.Set.name()).concat(Constants.INFERIEUR).concat("View").concat(nameChildEntity)
                            .concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attributeSelaveEntityName).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
            Attribute attribute = new Attribute();
            attribute.setNameAttribute(attributeSelaveEntityName);
            attribute.setTypeAttribute(null);
            attribute.setBuisness(ent);
            LOGGER.debug(attribute.getNameAttribute());
            attributesView.add(attribute);

        }
    }

    private String existEntityInParentTableOneToMany(String nameEntity, List<Relationship> relations) {
        LOGGER.debug("existEntityInParentTableOneToMany");
        String childrEntityName = null;
        for (Relationship relationship : relations) {
            if (relationship.getParentEntity().getNameEntity().equals(nameEntity) && relationship.getTypeRelationship().equals(RelationshipTypeEnum.OneToMany)
                    && relationship.getChildEntity().getNameEntity() != null) {
                childrEntityName = relationship.getChildEntity().getNameEntity();
                LOGGER.debug("{}", childrEntityName);
            }
        }
        return childrEntityName;

    }

    private List<Attribute> writeViewRelationshipsAttributesInDto(BuisnessLogicEntity entity) {
        LOGGER.debug("writeViewRelationshipsAttributesInDto");

        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < entity.getAttributes().size(); i++) {
            attributes.add(entity.getAttributes().get(i));
        }

        writeViewDtoOneToManyRelations(entity, attributes);
        writeViewDtoOneToOneRelations(entity, attributes);
        writeViewDtoManyToManyRelations(entity, attributes);

        return attributes;
    }

    private void writeViewDtoManyToManyRelations(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeViewDtoManyToManyRelations");
        List<String> relationshipsNamesManyToMany = entity.getRelationshipsParent().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.ManyToMany.name()))
                .map(relationship -> relationship.getChildEntity().getNameEntity()).collect(Collectors.toList());

        for (int z = 0; z < relationshipsNamesManyToMany.size(); z++) {
            Attribute attManyToMany = new Attribute();
            attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("s"));
            attManyToMany.setTypeAttribute(AttributesTypeEnum.Set);
            String existantNameAttribute = null;
            for (Attribute attribute : attributes) {
                if (attribute.getNameAttribute().equals(attManyToMany.getNameAttribute())) {
                    existantNameAttribute = attribute.getNameAttribute();
                }
            }

            if (existantNameAttribute == null) {
                attributes.add(attManyToMany);
            }
        }
    }

    private void writeViewDtoOneToOneRelations(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeViewDtoOneToOneRelations");
        List<String> relationshipsNamesOneToOne = entity.getRelationshipsParent().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
                .map(relationship -> relationship.getChildEntity().getNameEntity()).collect(Collectors.toList());

        for (int i = 0; i < relationshipsNamesOneToOne.size(); i++) {

            Attribute attOneToOne = new Attribute();
            attOneToOne.setNameAttribute(relationshipsNamesOneToOne.get(i).toLowerCase());
            attOneToOne.setTypeAttribute(AttributesTypeEnum.List);
            attributes.add(attOneToOne);

        }
    }

    private void writeViewDtoOneToManyRelations(BuisnessLogicEntity entity, List<Attribute> attributes) {
        LOGGER.debug("writeViewDtoOneToManyRelations");
        List<String> relationshipsNamesOneToMany = entity.getRelationshipsChild().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getParentEntity().getNameEntity()).collect(Collectors.toList());
        for (int i = 0; i < relationshipsNamesOneToMany.size(); i++) {

            Attribute attOneToMany = new Attribute();
            attOneToMany.setNameAttribute(relationshipsNamesOneToMany.get(i).toLowerCase());
            attOneToMany.setTypeAttribute(AttributesTypeEnum.List);
            attributes.add(attOneToMany);

        }
    }

    private void writeViewToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter) throws IOException {
        LOGGER.debug("writeViewToStringMethodInDto");

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_OVERRIDE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.TO_STRING_METHOD);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.DECLARATION_STRING_BUILDER);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(fileDto).concat(" ").concat(Constants.CROCHEE_OUVRANTE)
                .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        try {

            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.PATTERN_TABULATION);
            List<Attribute> attributes = ent.getAttributes();
            if (attributes != null && !attributes.isEmpty()) {
                if (!Constants.ID_MINUS.equals(attributes.get(0).getNameAttribute())) {
                    myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.ID_MINUS).concat(Constants.EGALE)
                            .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }
            } else {
                myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
                        .concat(Constants.ID_MINUS).concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
            }
            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                    .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        } catch (Exception e) {
            e.printStackTrace();
        }

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RETURN_BUILDER_TO_STRING);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void writeViewGettersAndSettersForId(FileWriter myWriter) {
        LOGGER.debug("writeViewGettersAndSettersForId");
        try {

            myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(AttributesTypeEnum.Long.name()).concat(" ").concat("get").concat(Constants.ID)
                    .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                    .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN)
                    .concat(Constants.ID_MINUS).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                    .concat(Constants.ACCOLADE_FERMANTE));
            myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(Constants.ID).concat(Constants.PARENTHESE_OUVRANTE)
                    .concat(AttributesTypeEnum.Long.name()).concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                    .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                    .concat(Constants.THIS).concat(Constants.ID_MINUS).concat(Constants.EGALE).concat(Constants.ID_MINUS).concat(Constants.PATTERN_POINT_VIRGULE)
                    .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));
            myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeViewIdAttribute(FileWriter myWriter) {
        LOGGER.debug("writeViewIdAttribute");

        try {
            myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(AttributesTypeEnum.Long.name()).concat(" ").concat("id")
                    .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private FileWriter writeViewImportsAndStructureOfClassInDto(Project project, BuisnessLogicEntity ent, List<Attribute> attributes) throws IOException {
        LOGGER.debug("writeViewImportsAndStructureOfClassInDto");
        String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(fileDto).concat(".java"));

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_SET);
        ent.getAttributes().stream().filter(attribute -> attribute.getTypeAttribute().toString().equals(AttributesTypeEnum.LocalDate.toString())).forEach(attribute -> {
            try {
                myWriter.write(ConstantsImportPackage.IMPORT_LOCAL_DATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        attributes.stream().forEach(attribute -> {
            String firstLetter = attribute.getNameAttribute().charAt(0) + "";
            String view = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length());
            String viewDto = view.substring(0, view.length() - 1);
            try {

                if (attribute.getNameAttribute().endsWith("s") && !attribute.getNameAttribute().endsWith("ss")) {
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute().substring(0, attribute.getNameAttribute().length() - 1))
                            .concat(Constants.POINT).concat("View").concat(viewDto).concat("Dto").concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }
                if (!attribute.getNameAttribute().endsWith("s") && attribute.getTypeAttribute().toString().equals(AttributesTypeEnum.List.toString())) {
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute()).concat(Constants.POINT).concat("View").concat(view).concat("Dto")
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        attributes.stream().forEach(attribute -> {
            if (Boolean.TRUE.equals(ent.getCreateListDtosIfChild()) && attribute.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name())) {
                try {
                    String first = firstLetterUpperCase(attribute);
                    first = first.substring(0, first.length() - 1);
                    String dto = first.concat("Dto");
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute().substring(0, attribute.getNameAttribute().length() - 1))
                            .concat(Constants.POINT).concat(dto).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_EMPTY);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_NULL);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_SIZE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_CONSTANTS);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_I_WEB_SERVICES_VALIDATORS);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

}
