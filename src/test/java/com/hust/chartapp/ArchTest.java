package com.hust.chartapp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.hust.chartapp");

        noClasses()
            .that()
            .resideInAnyPackage("com.hust.chartapp.service..")
            .or()
            .resideInAnyPackage("com.hust.chartapp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.hust.chartapp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
