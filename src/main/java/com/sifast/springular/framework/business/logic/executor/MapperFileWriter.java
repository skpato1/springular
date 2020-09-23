package com.sifast.springular.framework.business.logic.executor;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.service.impl.RelationshipService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperFileWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperFileWriter.class);

    Boolean checkManyParent = false;

    Boolean checkManyChild = false;

    String checkParentEntityOfChildRelationShips = null;

    String checkChildEntityOfChildRelaionShips = null;

    String checkParentEntityOfParentRelationShips = null;

    String checkChildEntityOfParentRelationShips = null;

    @Autowired
    RelationshipService relationshipService;

    public void generateMapperFiles(BuisnessLogicEntity ent, Project project) {
        try {
            List<Relationship> relationsParent = relationshipService.findByParentEntity(ent);
            List<Relationship> relationsChild = relationshipService.findByChildEntity(ent);
            List<BuisnessLogicEntity> entitiesParent = relationsChild.stream().map(Relationship::getParentEntity).collect(Collectors.toList());
            List<BuisnessLogicEntity> entitiesChild = relationsParent.stream().map(Relationship::getChildEntity).collect(Collectors.toList());
            FileWriter myWriter = writeImportsAndStructureOfClassInMappers(project, ent, entitiesParent, entitiesChild);
            injectServicesAndCongigMapper(ent, myWriter, entitiesParent, entitiesChild);
            writeCreateMapper(ent, myWriter, entitiesParent, entitiesChild);
            writeViewCreateMapper(ent, myWriter, entitiesParent, entitiesChild);
            writeViewMapper(ent, myWriter);
            writeMapToVersionDtoMapper(ent, myWriter);
            closeAccoladeAndFile(myWriter);
            checkImportsViewDtoInMappers(ent, project);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeViewCreateMapper(BuisnessLogicEntity ent, FileWriter myWriter, List<BuisnessLogicEntity> parentEntities, List<BuisnessLogicEntity> childEntities)
            throws IOException {
        String fileDto = ent.getNameEntity().toLowerCase().concat("Dto");
        String viewFileDto = "View".concat(ent.getNameEntity()).concat("Dto");
        retourLigneAndTabulation(myWriter);
        myWriter.write(Constants.PUBLIC.concat(ent.getNameEntity()).concat(" ").concat("mapView").concat(ent.getNameEntity()).concat("DtoTo").concat(ent.getNameEntity())
                .concat(Constants.PARENTHESE_OUVRANTE).concat(viewFileDto).concat(" ").concat(fileDto).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.THROWS)
                .concat(Constants.CUSTOM).concat(Constants.EXCEPTION).concat(" ").concat(Constants.ACCOLADE_OUVRANT));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(ent.getNameEntity()).concat(" " + Constants.MAPPED).concat(ent.getNameEntity())
                .concat(Constants.EGALE).concat(Constants.METHODE_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(fileDto).concat(Constants.VIRGULE).concat(ent.getNameEntity())
                .concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        childEntities.stream().forEach(entity -> {
            try {
                if (Boolean.TRUE.equals(entity.getCreateListIdsIfChild())) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(AttributesTypeEnum.Set.toString()).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.EGALE)
                            .concat(Constants.HASHSET_DECLARATION).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE)
                            .concat(Constants.POINT).concat(Constants.FIND_BY_IN).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto")
                            .concat(Constants.POINT).concat("get").concat(entity.getNameEntity()).concat("Ids").concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.IF).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("s")
                            .concat(Constants.POINT).concat(Constants.IS_EMPTY).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                            .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(Constants.THROW_NEW)
                            .concat(Constants.CUSTOM).concat(Constants.EXCEPTION).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.API_MESSAGE_STRING).concat(Constants.POINT)
                            .concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE)

                    );
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.MAPPED).concat(ent.getNameEntity()).concat(Constants.POINT)
                            .concat(Constants.SET_METHOD).concat(entity.getNameEntity()).concat("s").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.HASHSET_DECLARATION)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)

                    );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ent.getRelationshipsChild().forEach(childEntity -> {
            if (!childEntity.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
                checkManyChild = true;
                checkParentEntityOfChildRelationShips = childEntity.getParentEntity().getNameEntity();
                checkChildEntityOfChildRelaionShips = childEntity.getChildEntity().getNameEntity();

            }

        });

        ent.getRelationshipsParent().forEach(parentEntity -> {
            if (!parentEntity.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
                checkManyParent = true;
                checkParentEntityOfParentRelationShips = parentEntity.getParentEntity().getNameEntity();
                checkChildEntityOfParentRelationShips = parentEntity.getChildEntity().getNameEntity();

            }

        });
        if (Boolean.TRUE.equals(checkManyParent) && Boolean.TRUE.equals(checkManyChild)
                && (ent.getNameEntity().equals(checkParentEntityOfChildRelationShips) || ent.getNameEntity().equals(checkChildEntityOfChildRelaionShips)
                || ent.getNameEntity().equals(checkParentEntityOfParentRelationShips) || ent.getNameEntity().equals(checkChildEntityOfParentRelationShips))) {
            parentEntities.forEach(entity -> {

                try {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.OPTIONAL).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat(Constants.EGALE)
                            .concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE).concat(Constants.POINT).concat(Constants.FIND_BY_ID)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto").concat(Constants.POINT).concat("get")
                            .concat(entity.getNameEntity()).concat("Id").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.IF).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.NOT)
                            .concat(entity.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(Constants.IS_PRESENT_METHOD).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.PATTERN_TABULATION).concat(Constants.THROW_NEW).concat(Constants.CUSTOM).concat(Constants.EXCEPTION)
                            .concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.API_MESSAGE_STRING).concat(Constants.POINT).concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE)

                    );

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.MAPPED).concat(ent.getNameEntity()).concat(Constants.POINT)
                            .concat(Constants.SET_METHOD).concat(entity.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase())
                            .concat(Constants.POINT).concat(Constants.GET_METHOD).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }

        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN).concat(" " + Constants.MAPPED).concat(ent.getNameEntity())
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));

    }

    private void writeMapToVersionDtoMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String fileDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String entryVariable = ent.getNameEntity().toLowerCase();
        String variableToSet = ent.getNameEntity().toLowerCase().concat("Dto");
        String entityToRetrieve = "entity";
        String result = "dto";
        String existEntityInParentTableOneToMany = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
        if (existEntityInParentTableOneToMany != null) {
            generateParentMapper(ent, myWriter, fileDto, entryVariable, variableToSet, entityToRetrieve, result);
        } else {
            generateChildMapper(ent, myWriter, fileDto, entryVariable, variableToSet, entityToRetrieve, result);
        }
    }

    private void generateParentMapper(BuisnessLogicEntity ent, FileWriter myWriter, String fileDto, String entryVariable, String variableToSet, String entityToRetrieve,
            String result) throws IOException {
        retourLigneAndTabulation(myWriter);
        signatureMapToVersionDto(myWriter, fileDto, entryVariable);
        instanceObjectToReturn(myWriter, fileDto, variableToSet, result);
        conditionEntryVariableNotNull(myWriter, entryVariable);
        mapVersionWithoutEntity(myWriter, entryVariable, result);
        conditionIsRetrievedEntityInstanceOfThisEntity(myWriter, ent, entryVariable);
        retrieveEntityFromEntryVariable(ent, myWriter, entryVariable, entityToRetrieve);
        mappingRetrievedEntityToDto(myWriter, fileDto, variableToSet, entityToRetrieve);
        setChildAttributes(myWriter, variableToSet, entityToRetrieve, ent);
        closeCondition(myWriter);
        closeCondition(myWriter);
        setVariableToSetInResult(myWriter, result, variableToSet);
        returnResult(myWriter, result);
        closeCondition(myWriter);
        mapVersionWithoutEntityImplementation(myWriter, entryVariable, result, fileDto);
    }

    private void generateChildMapper(BuisnessLogicEntity ent, FileWriter myWriter, String fileDto, String entryVariable, String variableToSet, String entityToRetrieve,
            String result) throws IOException {
        retourLigneAndTabulation(myWriter);
        signatureMapToVersionDto(myWriter, fileDto, entryVariable);
        instanceObjectToReturn(myWriter, fileDto, variableToSet, result);
        conditionEntryVariableNotNull(myWriter, entryVariable);
        mapVersionWithoutEntity(myWriter, entryVariable, result);
        conditionIsRetrievedEntityInstanceOfThisEntity(myWriter, ent, entryVariable);
        retrieveEntityFromEntryVariable(ent, myWriter, entryVariable, entityToRetrieve);
        mappingRetrievedEntityToDto(myWriter, fileDto, variableToSet, entityToRetrieve);
        closeCondition(myWriter);
        setVariableToSetInResult(myWriter, result, variableToSet);
        closeCondition(myWriter);
        returnResult(myWriter, result);
        closeCondition(myWriter);
        mapVersionWithoutEntityImplementation(myWriter, entryVariable, result, fileDto);
    }

    private void retourLigneAndTabulation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
    }

    private void ifConditionIsInstanceOfChildren(FileWriter myWriter, BuisnessLogicEntity ent, String entityToRetrieve)
            throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(entityToRetrieve).concat(Constants.POINT).concat(Constants.GET)
                .concat(ent.getRelationshipsChild().get(0).getParentEntity().getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.NOT_EGALE).concat(Constants.NULL).concat(Constants.PARENTHESE_FERMANTE));

    }

    private void initializeVariableToRetrieveChildId(FileWriter myWriter) throws IOException {
        String idToInitialize = "id";
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.INT.concat(" ").concat(idToInitialize).concat(Constants.EGALE).concat("0").concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void retrieveParentEntityFromEntryVariable(BuisnessLogicEntity ent, FileWriter myWriter, String entryVariable, String entityToRetrieve) {
        ent.getRelationshipsChild().forEach(parentEntity -> {
            String parentEntityToRetrieve = entityToRetrieve.concat(parentEntity.getParentEntity().getNameEntity());
            try {
                retrieveEntityFromEntryVariable(parentEntity.getParentEntity(), myWriter, entryVariable, parentEntityToRetrieve);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void trackChildrenImplementation(FileWriter myWriter, String fileDto, String entryVariable, String variableToSet,
            String existEntityInParentTableOneToMany, BuisnessLogicEntity ent, String entityToRetrieve) throws IOException {
        retourLigneAndTabulation(myWriter);
        signatureTrackChildren(myWriter, fileDto, entryVariable, variableToSet);
        conditionOfInstanceOfChildren(myWriter, entryVariable, existEntityInParentTableOneToMany);
        castEntryVariableToChildrenEntity(myWriter, ent, existEntityInParentTableOneToMany, entryVariable, entityToRetrieve);
        mappingRetrievedEntityToDto(myWriter, fileDto, variableToSet, entityToRetrieve);
        setChildAttributes(myWriter, variableToSet, entityToRetrieve, ent);
        closeCondition(myWriter);
        returnResult(myWriter, variableToSet);
        closeCondition(myWriter);
    }

    private void castEntryVariableToChildrenEntity(FileWriter myWriter, BuisnessLogicEntity ent, String existEntityInParentTableOneToMany, String entryVariable,
            String entityToRetrieve) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ent.getNameEntity().concat(" ").concat(entityToRetrieve).concat(" ").concat(Constants.EGALE).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(existEntityInParentTableOneToMany).concat(Constants.PARENTHESE_FERMANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.METHOD_GET_ENTITY).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.POINT).concat(Constants.GET_LOWERCASE).concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)

        );

    }

    private void conditionOfInstanceOfChildren(FileWriter myWriter, String entryVariable, String existEntityInParentTableOneToMany) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT).concat(Constants.GET_ENTITY_METHOD).concat(" ")
                .concat(Constants.IS_INSTANCE_OF).concat(" ").concat(existEntityInParentTableOneToMany).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void signatureTrackChildren(FileWriter myWriter, String fileDto, String entryVariable, String variableToSet) throws IOException {
        myWriter.write(Constants.PRIVATE.concat(fileDto).concat(" ").concat(Constants.TRACK_CHILDREN_METHOD).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.VERSION_DTO_GENERIC).concat(" ").concat(entryVariable).concat(Constants.VIRGULE).concat(fileDto).concat(" ").concat(variableToSet)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void mapVersionWithoutEntityImplementation(FileWriter myWriter, String entryVariable, String result, String fileDto) throws IOException {
        retourLigneAndTabulation(myWriter);
        signatureMapVersionWithoutEntity(myWriter, entryVariable, result, fileDto);
        implementationOfMapVersionWithoutEntity(myWriter, entryVariable, result);
        closeCondition(myWriter);
    }

    private void implementationOfMapVersionWithoutEntity(FileWriter myWriter, String entryVariable, String result) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_AUTHOR).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_AUTHOR).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_CREATEDAT).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_CREATEDAT).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_CURRENT_VERSION).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_CURRENT_VERSION).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_UPDATEDAT).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_UPDATEDAT).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_VERSION).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_VERSION).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void signatureMapVersionWithoutEntity(FileWriter myWriter, String entryVariable, String result, String fileDto) throws IOException {
        myWriter.write(Constants.PRIVATE.concat(Constants.VOID).concat(Constants.MAP_VERSION_WITHOUT_ENTITY_METHOD).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.VERSION_DTO_GENERIC).concat(" ").concat(entryVariable).concat(Constants.VIRGULE).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR)
                .concat(fileDto).concat(Constants.SUPERIEUR).concat(" ").concat(result).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void callTrackChildren(FileWriter myWriter, String entryVariable, String variableToSet) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(variableToSet.concat(Constants.EGALE).concat(Constants.TRACK_CHILDREN_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable)
                .concat(Constants.VIRGULE).concat(variableToSet).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void setChildAttributes(FileWriter myWriter, String variableToSet, String entityToRetrieve, BuisnessLogicEntity ent) throws IOException {
        String childClass = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
        String streamVariable = "peek";
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        if (childClass != null) {
            String viewChildClassDto = "View".concat(childClass).concat("Dto");
            String getterChildClass = childClass.toLowerCase().concat("s");
            String first = getterChildClass.charAt(0) + "";
            String getterAttribute = first.toUpperCase().concat(getterChildClass.substring(1, getterChildClass.length()));
            try {
                myWriter.write(AttributesTypeEnum.Set.toString().concat(Constants.INFERIEUR).concat(viewChildClassDto).concat(Constants.SUPERIEUR).concat(" ")
                        .concat(streamVariable).concat(Constants.EGALE).concat(variableToSet).concat(Constants.POINT).concat("get").concat(getterAttribute)
                        .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT).concat(Constants.STREAM_METHOD).concat(Constants.POINT)
                        .concat(Constants.PEEK_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.ELEMENT_PREDICATE).concat(Constants.POINT)
                        .concat(Constants.SET_METHOD).concat(ent.getNameEntity()).concat(Constants.ID).concat(Constants.PARENTHESE_OUVRANTE).concat(entityToRetrieve)
                        .concat(Constants.POINT).concat(Constants.GET_LOWERCASE).concat(Constants.ID).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                        .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT).concat(Constants.COLLECT_METHOD)
                        .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.COLLECTORS_SET).concat(Constants.PARENTHESE_FERMANTE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(variableToSet)
                        .concat(Constants.POINT).concat("set").concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE).concat(streamVariable)
                        .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setVariableToSetInResult(FileWriter myWriter, String result, String variableToSet) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(result.concat(Constants.POINT).concat(Constants.SET_ENTITY_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(variableToSet)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void returnResult(FileWriter myWriter, String result) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RETURN.concat(" ").concat(result).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void closeCondition(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.ACCOLADE_FERMANTE.concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void conditionIsRetrievedEntityInstanceOfThisEntity(FileWriter myWriter, BuisnessLogicEntity ent, String entryVariable) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT).concat(Constants.METHOD_GET_ENTITY)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.IS_INSTANCE_OF).concat(" ").concat(ent.getNameEntity())
                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT.concat(Constants.PATTERN_RETOUR_LIGNE)));

    }

    private void mapVersionWithoutEntity(FileWriter myWriter, String entryVariable, String result) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.MAP_VERSION_WITHOUT_ENTITY_METHOD.concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.VIRGULE).concat(result)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void mappingRetrievedEntityToDto(FileWriter myWriter, String fileDto, String variableToSet, String entityToRetrieve) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(variableToSet.concat(Constants.EGALE).concat(Constants.MODELMAPPER_MAP_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(entityToRetrieve)
                .concat(Constants.VIRGULE).concat(fileDto).concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void retrieveEntityFromEntryVariable(BuisnessLogicEntity ent, FileWriter myWriter, String entryVariable, String entityToRetrieve) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ent.getNameEntity().concat(" ").concat(entityToRetrieve).concat(Constants.EGALE).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity())
                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(entryVariable).concat(Constants.POINT).concat(Constants.METHOD_GET_ENTITY)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
    }

    private void conditionEntryVariableNotNull(FileWriter myWriter, String entryVariable) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.NOT_EGALE).concat(Constants.NULL)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void instanceObjectToReturn(FileWriter myWriter, String fileDto, String variableToSet, String result)
            throws IOException {

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.VERSION_DTO.concat(Constants.INFERIEUR).concat(fileDto).concat(Constants.SUPERIEUR).concat(" ").concat(result).concat(" ").concat(Constants.EGALE)
                .concat(Constants.NEW).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(fileDto).concat(Constants.SUPERIEUR).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(fileDto.concat(" ").concat(variableToSet).concat(Constants.EGALE).concat(Constants.NEW).concat(fileDto).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
    }

    private void signatureMapToVersionDto(FileWriter myWriter, String fileDto, String entryVariable) throws IOException {

        myWriter.write(Constants.PUBLIC.concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(fileDto).concat(Constants.SUPERIEUR).concat(" ")
                .concat(Constants.MAP_TO_VERSION_DTO).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR)
                .concat(Constants.POINT_INTERROGATION).concat(Constants.SUPERIEUR).concat(" ").concat(entryVariable).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                .concat(Constants.THROWS).concat(Constants.EXCEPTION).concat(" ").concat(Constants.ACCOLADE_OUVRANT));
    }

    private void writeViewMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = fileViewDto.toLowerCase();
        retourLigneAndTabulation(myWriter);
        myWriter.write(Constants.PUBLIC.concat(fileViewDto).concat(" ").concat("map").concat(ent.getNameEntity()).concat("To").concat(fileViewDto)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity()).concat(" ").concat(ent.getNameEntity().toLowerCase()).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(fileViewDto).concat(" ").concat(fileDto)
                .concat(Constants.EGALE).concat(Constants.METHODE_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat(Constants.VIRGULE)
                .concat(fileViewDto).concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN).concat(fileDto).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));
    }

    private void writeCreateMapper(BuisnessLogicEntity ent, FileWriter myWriter, List<BuisnessLogicEntity> parentEntities, List<BuisnessLogicEntity> childEntities)
            throws IOException {
        String fileDto = ent.getNameEntity().toLowerCase().concat("Dto");

        retourLigneAndTabulation(myWriter);
        myWriter.write(Constants.PUBLIC.concat(ent.getNameEntity()).concat(" ").concat("map").concat(ent.getNameEntity()).concat("DtoTo").concat(ent.getNameEntity())
                .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity()).concat("Dto ").concat(fileDto).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                .concat(Constants.THROWS).concat(Constants.CUSTOM).concat(Constants.EXCEPTION).concat(" ").concat(Constants.ACCOLADE_OUVRANT));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(ent.getNameEntity()).concat(" " + Constants.MAPPED).concat(ent.getNameEntity())
                .concat(Constants.EGALE).concat(Constants.METHODE_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(fileDto).concat(Constants.VIRGULE).concat(ent.getNameEntity())
                .concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        childEntities.stream().forEach(entity -> {
            try {
                if (Boolean.TRUE.equals(entity.getCreateListIdsIfChild())) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(AttributesTypeEnum.Set.toString()).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.EGALE)
                            .concat(Constants.HASHSET_DECLARATION).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE)
                            .concat(Constants.POINT).concat(Constants.FIND_BY_IN).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto")
                            .concat(Constants.POINT).concat("get").concat(entity.getNameEntity()).concat("Ids").concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.IF).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("s")
                            .concat(Constants.POINT).concat(Constants.IS_EMPTY).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                            .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(Constants.THROW_NEW)
                            .concat(Constants.CUSTOM).concat(Constants.EXCEPTION).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.API_MESSAGE_STRING).concat(Constants.POINT)
                            .concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE)

                    );
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.MAPPED).concat(ent.getNameEntity()).concat(Constants.POINT)
                            .concat(Constants.SET_METHOD).concat(entity.getNameEntity()).concat("s").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.HASHSET_DECLARATION)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)

                    );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ent.getRelationshipsChild().forEach(childEntity -> {
            if (!childEntity.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
                checkManyChild = true;
                checkParentEntityOfChildRelationShips = childEntity.getParentEntity().getNameEntity();
                checkChildEntityOfChildRelaionShips = childEntity.getChildEntity().getNameEntity();

            }

        });

        ent.getRelationshipsParent().forEach(parentEntity -> {
            if (!parentEntity.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
                checkManyParent = true;
                checkParentEntityOfParentRelationShips = parentEntity.getParentEntity().getNameEntity();
                checkChildEntityOfParentRelationShips = parentEntity.getChildEntity().getNameEntity();

            }

        });
        if (Boolean.TRUE.equals(checkManyParent) && Boolean.TRUE.equals(checkManyChild)
                && (ent.getNameEntity().equals(checkParentEntityOfChildRelationShips) || ent.getNameEntity().equals(checkChildEntityOfChildRelaionShips)
                || ent.getNameEntity().equals(checkParentEntityOfParentRelationShips) || ent.getNameEntity().equals(checkChildEntityOfParentRelationShips))) {
            parentEntities.forEach(entity -> {

                try {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.OPTIONAL).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat(Constants.EGALE)
                            .concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE).concat(Constants.POINT).concat(Constants.FIND_BY_ID)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto").concat(Constants.POINT).concat("get")
                            .concat(entity.getNameEntity()).concat("Id").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.IF).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.NOT)
                            .concat(entity.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(Constants.IS_PRESENT_METHOD).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.PATTERN_TABULATION).concat(Constants.THROW_NEW).concat(Constants.CUSTOM).concat(Constants.EXCEPTION)
                            .concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.API_MESSAGE_STRING).concat(Constants.POINT).concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                            .concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE)

                    );

                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.MAPPED).concat(ent.getNameEntity()).concat(Constants.POINT)
                            .concat(Constants.SET_METHOD).concat(entity.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase())
                            .concat(Constants.POINT).concat(Constants.GET_METHOD).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }

        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN).concat(" " + Constants.MAPPED).concat(ent.getNameEntity())
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));

    }

    private void injectServicesAndCongigMapper(BuisnessLogicEntity ent, FileWriter myWriter, List<BuisnessLogicEntity> parentEntities, List<BuisnessLogicEntity> childEntities)
            throws IOException {

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.INJECT_MODEL_MAPPER);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

        parentEntities.stream().forEach(entity -> {

            try {
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write("I".concat(entity.getNameEntity()).concat(Constants.SERVICE + " ").concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        childEntities.stream().forEach(entity -> {

            try {
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write("I".concat(entity.getNameEntity()).concat(Constants.SERVICE + " ").concat(entity.getNameEntity().toLowerCase()).concat(Constants.SERVICE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write("I".concat(ent.getNameEntity()).concat("Service ").concat(ent.getNameEntity().toLowerCase()).concat(Constants.SERVICE)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

    }

    private FileWriter writeImportsAndStructureOfClassInMappers(Project project, BuisnessLogicEntity ent, List<BuisnessLogicEntity> parentEntities,
            List<BuisnessLogicEntity> childEntities) throws IOException {
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_MAPPER).concat(ent.getNameEntity()).concat("Mapper.java"));
        String fileMapper = ent.getNameEntity().concat("Mapper");
        String fileCreateDto = ent.getNameEntity().concat("Dto");
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = ent.getNameEntity().concat("Dto");
        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_MAPPER.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_AUTOWIRED);
        myWriter.write(ConstantsImportPackage.IMPORT_COLLECTORS);
        myWriter.write(ConstantsImportPackage.IMPORT_COMPONENT);
        myWriter.write(ConstantsImportPackage.IMPORT_HASH_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_OPTIONAL);
        myWriter.write(ConstantsImportPackage.IMPORT_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_EXCEPTION.concat(Constants.CUSTOM).concat(Constants.EXCEPTION).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_CONFIGURE_MODEL_MAPPER);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileCreateDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileViewDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        parentEntities.stream().forEach(entity -> {
            try {
                myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(entity.getNameEntity()).concat(Constants.SERVICE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        childEntities.stream().forEach(entity -> {
            try {
                myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(entity.getNameEntity()).concat(Constants.SERVICE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        myWriter.write(
                ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(ent.getNameEntity()).concat(Constants.SERVICE)
                        .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(ent.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_VERSION_DTO);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_COMPONENT);
        myWriter.write(
                Constants.PUBLIC_CLASS.concat(fileMapper).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;

    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();

    }

    private String existEntityInParentTableOneToMany(String nameEntity, List<Relationship> relations) {
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

    private void checkImportsViewDtoInMappers(BuisnessLogicEntity ent, Project project) throws IOException {
        String child = existEntityInParentTableOneToMany(ent.getNameEntity(), ent.getRelationshipsParent());
        if (child != null) {
            String viewChildDto = "View".concat(child).concat("Dto");
            File file = new File(
                    ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_MAPPER.concat(ent.getNameEntity()).concat("Mapper.java")));
            String fileContext = FileUtils.readFileToString(file);
            fileContext = fileContext.replaceAll(ConstantsImportPackage.IMPORT_VERSION_DTO, ConstantsImportPackage.IMPORT_VERSION_DTO.concat(ConstantsImportPackage.IMPORT_DTO
                    .concat(child.toLowerCase()).concat(Constants.POINT).concat(viewChildDto).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE)));
            FileUtils.write(file, fileContext);
        }
    }

}
