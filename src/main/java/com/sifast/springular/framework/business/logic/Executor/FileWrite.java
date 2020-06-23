package com.sifast.springular.framework.business.logic.Executor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;

@Component
public class FileWrite {

    public void writeRelationshipsInJdlFile(Project project, FileWriter myWriter) {
        List<Integer> usedRelationshipsIds = new ArrayList<>();
        project.getEntities().stream().forEach(entity -> {
            entity.getRelationshipsParent().stream().forEach(parentRelationship -> {
                addRelationshipToJdlFile(usedRelationshipsIds, parentRelationship, myWriter);
            });
            entity.getRelationshipsChild().stream().forEach(childRelationship -> {
                addRelationshipToJdlFile(usedRelationshipsIds, childRelationship, myWriter);
            });
        });
    }

    public void writeEntitiesInJdlFile(Project project, FileWriter myWriter) {
        project.getEntities().stream().forEach(entity -> {
            try {
                writeEntityNameInJdlFile(myWriter, entity);
                entity.getAttributes().stream().forEach(attribute -> {
                    try {
                        writeAttributeNameInJdlFile(myWriter, attribute);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                myWriter.write(Constants.ACCOLADE_FERMANTE.concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(Constants.ENABLE_DTO_GENERATION.concat(Constants.PATTERN_RETOUR_LIGNE));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void writeProjectDetailsInJdlFile(Project project, FileWriter myWriter) throws IOException {
        myWriter.write("application ".concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("config ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("baseName ").concat(Constants.NAME_PROJECT).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("packageName ").concat(ConstantsImportPackage.PATTERN_PACKAGE_NAME).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("applicationType ").concat(project.getTypeProject()).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("serverPort ").concat(project.getPortProject()).concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat("enableHibernateCache false").concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.ACCOLADE_FERMANTE.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write("entities *".concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(Constants.ACCOLADE_FERMANTE.concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    public void writeAttributeNameInJdlFile(FileWriter myWriter, Attribute attribute) throws IOException {
        myWriter.write(
                Constants.PATTERN_TABULATION.concat(attribute.getNameAttribute()).concat(" ").concat(attribute.getTypeAttribute().name()).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    public void writeEntityNameInJdlFile(FileWriter myWriter, BuisnessLogicEntity entity) throws IOException {
        myWriter.write(ConstantsAnnotations.ANNOTATION_NO_FLUENT_METHOD.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write("entity ".concat(entity.getNameEntity()).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    public void addRelationshipToJdlFile(List<Integer> usedRelationshipsIds, Relationship relationship, FileWriter myWriter) {
        if (!usedRelationshipsIds.contains(relationship.getId())) {
            writeRelationshipInFile(myWriter, relationship);
            usedRelationshipsIds.add(relationship.getId());
        }
    }

    public void writeRelationshipInFile(FileWriter myWriter, Relationship relationship) {
        try {
            if (!relationship.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
                myWriter.write("relationship ".concat(relationship.getTypeRelationship().name()).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_TABULATION.concat(relationship.getParentEntity().getNameEntity()).concat(Constants.ACCOLADE_OUVRANT)
                        .concat(relationship.getChildEntity().getNameEntity().toLowerCase()).concat(Constants.ACCOLADE_FERMANTE).concat(" ").concat("to ")
                        .concat(relationship.getChildEntity().getNameEntity()).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_FERMANTE));
            } else {
                myWriter.write("relationship ".concat(relationship.getTypeRelationship().name()).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_TABULATION.concat(relationship.getParentEntity().getNameEntity()).concat(Constants.ACCOLADE_OUVRANT)
                        .concat(relationship.getChildEntity().getNameEntity().toLowerCase()).concat(Constants.ACCOLADE_FERMANTE).concat("to ")
                        .concat(relationship.getChildEntity().getNameEntity()).concat(Constants.ACCOLADE_OUVRANT)
                        .concat(relationship.getParentEntity().getNameEntity().toLowerCase()).concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE)
                        .concat(Constants.ACCOLADE_FERMANTE));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
