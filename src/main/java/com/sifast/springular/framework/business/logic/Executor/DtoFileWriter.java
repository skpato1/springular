package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class DtoFileWriter {

    public void generateSuperFilesInEachFolderDTO(BuisnessLogicEntity entity, Project project) {
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
        List<String> relationshipsNamesOneToMany = entity.getRelationshipsSlave().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getMasterEntity().getNameEntity()).collect(Collectors.toList());

        List<String> relationshipsNamesOneToOne = entity.getRelationshipsMaster().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
                .map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

        List<String> relationshipsNamesManyToMany = entity.getRelationshipsMaster().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.ManyToMany.name()))
                .map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

        List<String> relationshipsNamesOneToManyMaster = entity.getRelationshipsMaster().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < entity.getAttributes().size(); i++) {
            attributes.add(entity.getAttributes().get(i));
        }

        for (int i = 0; i < relationshipsNamesOneToManyMaster.size(); i++) {
            if (entity.getCreateListIdsIfSlave()) {
                Attribute attOneToManyMaster = new Attribute();
                attOneToManyMaster.setNameAttribute(relationshipsNamesOneToManyMaster.get(i).toLowerCase().concat("Ids"));
                attOneToManyMaster.setTypeAttribute(AttributesTypeEnum.Set);
                attributes.add(attOneToManyMaster);
            }

        }

        for (int i = 0; i < relationshipsNamesOneToMany.size(); i++) {

            Attribute attOneToMany = new Attribute();
            attOneToMany.setNameAttribute(relationshipsNamesOneToMany.get(i).toLowerCase().concat("Id"));
            attOneToMany.setTypeAttribute(AttributesTypeEnum.Long);
            attributes.add(attOneToMany);

        }
        for (int j = 0; j < relationshipsNamesOneToOne.size(); j++) {

            Attribute attOneToOne = new Attribute();
            attOneToOne.setNameAttribute(relationshipsNamesOneToOne.get(j).toLowerCase().concat("Id"));
            attOneToOne.setTypeAttribute(AttributesTypeEnum.Long);
            attributes.add(attOneToOne);
        }

        for (int z = 0; z < relationshipsNamesManyToMany.size(); z++) {

            Attribute attManyToMany = new Attribute();
            if (entity.getCreateListDtosIfSlave()) {
                attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("s"));
            } else {
                attManyToMany.setNameAttribute(relationshipsNamesManyToMany.get(z).toLowerCase().concat("Ids"));
            }
            attManyToMany.setTypeAttribute(AttributesTypeEnum.Set);
            attributes.add(attManyToMany);
        }
        return attributes;
    }

    private void writeToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter) throws IOException {
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
                .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        ent.getAttributes().stream().forEach(attribute -> {
            try {
                if (!attribute.getTypeAttribute().equals(AttributesTypeEnum.Set) && !attribute.getNameAttribute().contains("Id")) {
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.PATTERN_TABULATION);
                    List<Attribute> attributes = ent.getAttributes();
                    if (attributes.get(0).getNameAttribute() == attribute.getNameAttribute()) {
                        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(attribute.getNameAttribute())
                                .concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                    } else {
                        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
                                .concat(attribute.getNameAttribute()).concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                    }
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.PATTERN_TABULATION);
                    myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(attribute.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
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

    private FileWriter writeImportsAndStructureOfClassInDto(Project project, BuisnessLogicEntity ent, List<Attribute> attributes) throws IOException {
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(ent.getNameEntity()).concat("Dto.java"));
        String fileDto = ent.getNameEntity().concat("Dto");
        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_SET);

        ent.getAttributes().stream().forEach(attribute -> {
            if (attribute.getTypeAttribute().toString().equals("LocalDate") || attribute.getTypeAttribute().name().equals(AttributesTypeEnum.LocalDate)) {
                try {
                    myWriter.write(ConstantsImportPackage.IMPORT_LOCAL_DATE);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        attributes.stream().forEach(attribute -> {
            if (ent.getCreateListDtosIfSlave() && attribute.getTypeAttribute().name().equals(AttributesTypeEnum.Set.name())) {
                try {
                    String first = firstLetterUpperCase(attribute);
                    first = first.substring(0, first.length() - 1);
                    String dto = first.concat("Dto");
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute().substring(0, attribute.getNameAttribute().length() - 1))
                            .concat(Constants.POINT).concat(dto).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_EMPTY);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_NOT_NULL);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_VALIDATION_SIZE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_CONSTANTS);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO_IWebServicesValidators);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

    private void writeAttributesWithValidationAnnotationInDto(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributes) {

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
                            .concat(attribute.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                } else {
                    String firstLetter = "" + attribute.getNameAttribute().charAt(0);
                    String attributeDto = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length() - 1);
                    if (ent.getCreateListDtosIfSlave()) {
                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(attributeDto).concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attribute.getNameAttribute())
                                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                    } else {

                        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(attribute.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                                .concat(Constants.LONG).concat(" ").concat(attribute.getNameAttribute())

                                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                    }

                }
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            } catch (Exception e) {
            }

        });

    }

    private void writeGettersAndSettersForDto(FileWriter myWriter, List<Attribute> attributes, BuisnessLogicEntity ent) {
        attributes.stream().forEach(attributeForGetterAndSetter -> {
            String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
            String getterAttribute = firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
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
                    if (ent.getCreateListDtosIfSlave()) {
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
                    } else {

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
                // TODO: handle exception
            }
        });
    }

    public void generateCreateFilesInEachFolderDTO(Project project) {

        project.getEntities().stream().forEach(ent -> {
            try {
                @SuppressWarnings("unused")
                File fileCreate = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                        .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat("Create").concat(ent.getNameEntity().concat("Dto.java")));
                FileWriter myWriter = writeCreateImportsAndStructureOfClassInDto(project, ent);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private FileWriter writeCreateImportsAndStructureOfClassInDto(Project project, BuisnessLogicEntity ent) throws IOException {
        String fileDto = "Create".concat(ent.getNameEntity()).concat("Dto");
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(fileDto).concat(".java"));

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        ent.getAttributes().stream().forEach(attribute -> {
            if (attribute.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
                try {
                    myWriter.write(ConstantsImportPackage.IMPORT_SET);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.EXTENDS).concat(ent.getNameEntity()).concat("Dto").concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

    public void generateViewFilesInEachFolderDTO(Project project) {

        project.getEntities().stream().forEach(ent -> {
            try {
                List<Attribute> attributes = writeViewRelationshipsAttributesInDto(ent);
                @SuppressWarnings("unused")
                File fileView = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                        .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat("View").concat(ent.getNameEntity().concat("Dto.java")));
                String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
                FileWriter myWriter = writeViewImportsAndStructureOfClassInDto(project, ent, attributes);

                writeViewAttributesInDto(ent, myWriter);
                writeViewRelationshipAttributeFromAttribute(ent, myWriter, attributes);
                writeViewGettersAndSettersForDto(ent, myWriter);
                writeViewGettersAndSettersForRelationshipAttributes(ent, myWriter, attributes);
                writeViewToStringMethodInDto(ent, fileDto, myWriter);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void writeViewGettersAndSettersForRelationshipAttributes(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributes) {
        attributes.stream().forEach(attributeForGetterAndSetter -> {
            try {
                if (attributeForGetterAndSetter.getTypeAttribute().equals(AttributesTypeEnum.Set)) {
                    String typeSetDto = viewFileDto(attributeForGetterAndSetter);
                    String getterAttribute = firstLetterUpperCase(attributeForGetterAndSetter);

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR)
                            .concat(typeSetDto).concat(Constants.SUPERIEUR).concat(" ").concat("get").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN))
                            .concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE)));
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                    myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PUBLIC).concat(Constants.VOID).concat("set").concat(getterAttribute)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(attributeForGetterAndSetter.getTypeAttribute().name()).concat(Constants.INFERIEUR).concat(typeSetDto)
                            .concat(Constants.SUPERIEUR).concat(" ").concat(attributeForGetterAndSetter.getNameAttribute()).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                            .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS).concat(attributeForGetterAndSetter.getNameAttribute())
                                    .concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
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
                            .concat(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.THIS).concat(attributeForGetterAndSetter.getNameAttribute())
                                    .concat(Constants.EGALE).concat(attributeForGetterAndSetter.getNameAttribute()))
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

    private String firstLetterUpperCase(Attribute attributeForGetterAndSetter) {
        String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
        String getterAttribute = firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
        return getterAttribute;
    }

    private String viewFileDto(Attribute attributeForGetterAndSetter) {
        String firstLetterDto = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
        String viewDto = attributeForGetterAndSetter.getNameAttribute().substring(1, attributeForGetterAndSetter.getNameAttribute().length() - 1);
        String typeSetDto = "View".concat(firstLetterDto.toUpperCase()).concat(viewDto).concat("Dto");
        return typeSetDto;
    }

    private void writeViewRelationshipAttributeFromAttribute(BuisnessLogicEntity ent, FileWriter myWriter, List<Attribute> attributes) {
        attributes.stream().forEach(attribute -> {
            try {

                String firstLetter = attribute.getNameAttribute().charAt(0) + "";
                String view = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length());

                if (attribute.getTypeAttribute().equals(AttributesTypeEnum.List) && !attribute.getNameAttribute().endsWith("s")) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat("View").concat(view).concat("Dto").concat(" ").concat(attribute.getNameAttribute())
                            .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }

                if (attribute.getNameAttribute().endsWith("s")) {
                    view = view.substring(0, view.length() - 1);
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(AttributesTypeEnum.Set.name()).concat(Constants.INFERIEUR).concat("View")
                            .concat(view).concat("Dto").concat(Constants.SUPERIEUR).concat(" ").concat(attribute.getNameAttribute().concat("123"))
                            .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }

                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
            } catch (Exception e) {
            }

        });

    }

    private List<Attribute> writeViewRelationshipsAttributesInDto(BuisnessLogicEntity entity) {

        List<String> relationshipsNamesOneToMany = entity.getRelationshipsSlave().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToMany.name()))
                .map(relationship -> relationship.getMasterEntity().getNameEntity()).collect(Collectors.toList());

        List<String> relationshipsNamesOneToOne = entity.getRelationshipsMaster().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.OneToOne.name()))
                .map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

        List<String> relationshipsNamesManyToMany = entity.getRelationshipsMaster().stream()
                .filter(relationship -> relationship.getTypeRelationship().name().equals(RelationshipTypeEnum.ManyToMany.name()))
                .map(relationship -> relationship.getSlaveEntity().getNameEntity()).collect(Collectors.toList());

        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < entity.getAttributes().size(); i++) {
            attributes.add(entity.getAttributes().get(i));
        }
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

        return attributes;
    }

    private void writeViewToStringMethodInDto(BuisnessLogicEntity ent, String fileDto, FileWriter myWriter) throws IOException {

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
                .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        try {

            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.PATTERN_TABULATION);
            List<Attribute> attributes = ent.getAttributes();
            if (attributes != null && !attributes.isEmpty()) {
                if (attributes.get(0).getNameAttribute() != Constants.ID_MINUS) {
                    myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.ID_MINUS).concat(Constants.EGALE)
                            .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }
            } else {
                myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" ")
                        .concat(Constants.ID_MINUS).concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE)
                        .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
            }
            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.PATTERN_TABULATION);
            myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                    .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        } catch (Exception e) {
            e.printStackTrace();
        }

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.BUILDER_APPEND.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat(Constants.CROCHEE_FERMANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RETURN_BUILDER_TO_STRING);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void writeViewGettersAndSettersForDto(BuisnessLogicEntity ent, FileWriter myWriter) {

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
            // TODO: handle exception
        }

    }

    private void writeViewAttributesInDto(BuisnessLogicEntity ent, FileWriter myWriter) {

        try {
            myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PRIVATE).concat(AttributesTypeEnum.Long.name()).concat(" ").concat("id")
                    .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private FileWriter writeViewImportsAndStructureOfClassInDto(Project project, BuisnessLogicEntity ent, List<Attribute> attributes) throws IOException {
        String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(fileDto).concat(".java"));

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_SET);
        attributes.stream().forEach(attribute -> {
            String firstLetter = attribute.getNameAttribute().charAt(0) + "";
            String view = firstLetter.toUpperCase() + attribute.getNameAttribute().substring(1, attribute.getNameAttribute().length());
            String viewDto = view.substring(0, view.length() - 1);
            try {

                if (attribute.getNameAttribute().endsWith("s") && !attribute.getNameAttribute().endsWith("ss")) {
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute().substring(0, attribute.getNameAttribute().length() - 1))
                            .concat(Constants.POINT).concat("View").concat(viewDto).concat("Dto").concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }
                if (!attribute.getNameAttribute().endsWith("s") && attribute.getTypeAttribute().toString().equals(AttributesTypeEnum.List.toString())) {
                    myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(attribute.getNameAttribute()).concat(Constants.POINT).concat("View").concat(view).concat("Dto")
                            .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileDto).concat(Constants.EXTENDS).concat(ent.getNameEntity()).concat("Dto").concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

}
