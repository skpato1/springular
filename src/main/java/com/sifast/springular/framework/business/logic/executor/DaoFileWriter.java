package com.sifast.springular.framework.business.logic.executor;

import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Project;
import java.io.File;
import java.io.FileWriter;
import org.springframework.stereotype.Component;

@Component
public class DaoFileWriter {

    public void writeDaoFiles(Project project) {
        project.getEntities().stream().forEach(entity -> {
            try {
                File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES).concat("I")
                        + entity.getNameEntity().concat("Dao.java"));
                String fileDao = "I".concat(entity.getNameEntity()).concat("Dao");
                FileWriter myWriter = new FileWriter(file);
                myWriter.write("");
                myWriter.write(ConstantsImportPackage.PACKAGE_SPRINGULAR_DAO.concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_SPRINGULAR_DOMAIN_TO_REPLACE.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_SPRINGULAR_REPOSITORY_ALL.concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(ConstantsImportPackage.IMPORT_SPRINGULAR_REPOSITORY.concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(ConstantsAnnotations.ANNOTATION_REPOSITORY.concat(Constants.PATTERN_RETOUR_LIGNE));
                myWriter.write(Constants.PUBLIC_INTERFACE.concat(fileDao).concat(Constants.EXTENDS).concat(Constants.IGENERICDAO).concat(entity.getNameEntity())
                        .concat(Constants.VIRGULE).concat(Constants.LONG));
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.ACCOLADE_OUVRANT);
                myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
                myWriter.write(Constants.ACCOLADE_FERMANTE);
                myWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
