package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.service.impl.RelationshipService;

@Component
public class MapperFileWriter {

    @Autowired
    RelationshipService relationshipService;

    public void generateMapperFiles(BuisnessLogicEntity entity, Project project) {

        project.getEntities().stream().forEach(ent -> {
            try {
                List<Relationship> relationsMaster = relationshipService.findByMasterEntity(ent);
                List<Relationship> relationsSlave = relationshipService.findBySlaveEntity(ent);

                @SuppressWarnings("unused")
                // master fihem objets
                List<BuisnessLogicEntity> entitiesMaster = relationsSlave.stream().map(Relationship::getMasterEntity).collect(Collectors.toList());
                @SuppressWarnings("unused")
                // slave fihem list
                List<BuisnessLogicEntity> entitiesSlave = relationsMaster.stream().map(Relationship::getSlaveEntity).collect(Collectors.toList());

                FileWriter myWriter = writeImportsAndStructureOfClassInMappers(project, ent, entitiesMaster, entitiesSlave);
                injectServicesAndCongigMapper(ent, myWriter, entitiesMaster, entitiesSlave);
                writeCreateMapper(ent, myWriter, entitiesMaster, entitiesSlave);
                writeViewMapper(ent, myWriter);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeViewMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = fileViewDto.toLowerCase();
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PUBLIC.concat(fileViewDto).concat(" ").concat("map").concat(ent.getNameEntity()).concat("To").concat(fileViewDto)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity()).concat(" ").concat(ent.getNameEntity().toLowerCase()).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(fileViewDto).concat(" ").concat(fileDto)
                .concat(Constants.EGALE).concat(Constants.METHODE_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat(Constants.VIRGULE)
                .concat(fileViewDto).concat(".class").concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN).concat(fileDto).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));
    }

    private void writeCreateMapper(BuisnessLogicEntity ent, FileWriter myWriter, List<BuisnessLogicEntity> masterEntities, List<BuisnessLogicEntity> slaveEntities)
            throws IOException {
        String fileDto = ent.getNameEntity().toLowerCase().concat("Dto");

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.PUBLIC.concat(ent.getNameEntity()).concat(" ").concat("map").concat(ent.getNameEntity()).concat("DtoTo").concat(ent.getNameEntity())
                .concat(Constants.PARENTHESE_OUVRANTE).concat("Create").concat(ent.getNameEntity()).concat("Dto ").concat(fileDto).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(ent.getNameEntity()).concat(" mapped").concat(ent.getNameEntity())
                .concat(Constants.EGALE).concat(Constants.METHODE_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(fileDto).concat(Constants.VIRGULE).concat(ent.getNameEntity())
                .concat(".class").concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        slaveEntities.stream().forEach(entity -> {
            try {
                if (entity.getCreateListIdsIfSlave()) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(AttributesTypeEnum.Set.toString()).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.EGALE)
                            .concat(Constants.HASHSET_DECLARATION).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("Service")
                            .concat(Constants.POINT).concat(Constants.FIND_BY_IN).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto")
                            .concat(Constants.POINT).concat("get").concat(entity.getNameEntity()).concat("Ids").concat(Constants.PARENTHESE_OUVRANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));
                    myWriter.write(Constants.PATTERN_TABULATION.concat("mapped").concat(ent.getNameEntity()).concat(Constants.POINT).concat(Constants.SET_METHOD)
                            .concat(entity.getNameEntity()).concat("s").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.HASHSET_DECLARATION)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat("s").concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)

                    );
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        masterEntities.stream().forEach(entity -> {
            try {
                if (entity.getCreateListIdsIfSlave()) {
                    myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.OPTIONAL).concat(Constants.INFERIEUR)
                            .concat(entity.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(entity.getNameEntity().toLowerCase()).concat(Constants.EGALE)
                            .concat(entity.getNameEntity().toLowerCase()).concat("Service").concat(Constants.POINT).concat(Constants.FIND_BY_ID)
                            .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat("Dto").concat(Constants.POINT).concat("get")
                            .concat(entity.getNameEntity()).concat("Id").concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                            .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION));
                    myWriter.write(Constants.PATTERN_TABULATION.concat("mapped").concat(ent.getNameEntity()).concat(Constants.POINT).concat(Constants.SET_METHOD)
                            .concat(entity.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(entity.getNameEntity().toLowerCase()).concat(Constants.POINT)
                            .concat(Constants.GET_METHOD).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.PATTERN_TABULATION).concat(Constants.RETURN).concat(" mapped").concat(ent.getNameEntity())
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));

    }

    private void injectServicesAndCongigMapper(BuisnessLogicEntity ent, FileWriter myWriter, List<BuisnessLogicEntity> masterEntities, List<BuisnessLogicEntity> slaveEntities)
            throws IOException {

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.INJECT_MODEL_MAPPER);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

        masterEntities.stream().forEach(entity -> {

            try {
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write("I".concat(entity.getNameEntity()).concat("Service ").concat(entity.getNameEntity().toLowerCase()).concat("Service")
                        .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        slaveEntities.stream().forEach(entity -> {

            try {
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
                myWriter.write(Constants.PATTERN_TABULATION);
                myWriter.write("I".concat(entity.getNameEntity()).concat("Service ").concat(entity.getNameEntity().toLowerCase()).concat("Service")
                        .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write("I".concat(ent.getNameEntity()).concat("Service ").concat(ent.getNameEntity().toLowerCase()).concat("Service")
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);

    }

    private FileWriter writeImportsAndStructureOfClassInMappers(Project project, BuisnessLogicEntity ent, List<BuisnessLogicEntity> masterEntities,
            List<BuisnessLogicEntity> slaveEntities) throws IOException {
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_MAPPER).concat(ent.getNameEntity()).concat("Mapper.java"));
        String fileMapper = ent.getNameEntity().concat("Mapper");
        String fileCreateDto = "Create".concat(ent.getNameEntity()).concat("Dto");
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = ent.getNameEntity().concat("Dto");

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_MAPPER.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_AUTOWIRED);
        myWriter.write(ConstantsImportPackage.IMPORT_COMPONENT);
        myWriter.write(ConstantsImportPackage.IMPORT_HASH_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_OPTIONAL);

        myWriter.write(ConstantsImportPackage.IMPORT_CONFIGURE_MODEL_MAPPER);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileCreateDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileViewDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        masterEntities.stream().forEach(entity -> {
            try {
                myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(entity.getNameEntity()).concat("Service")
                        .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        slaveEntities.stream().forEach(entity -> {
            try {
                myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(entity.getNameEntity()).concat("Service")
                        .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat("I").concat(ent.getNameEntity()).concat("Service").concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(ent.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_COMPONENT);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileMapper).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;

    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();

    }

}
