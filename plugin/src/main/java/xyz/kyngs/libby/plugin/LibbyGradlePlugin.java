/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package xyz.kyngs.libby.plugin;

import com.grack.nanojson.JsonWriter;
import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.jvm.tasks.Jar;
import org.gradle.language.assembler.tasks.Assemble;
import org.gradle.language.jvm.tasks.ProcessResources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple 'hello world' plugin.
 */
public class LibbyGradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getExtensions().create("libby", LibbyExtension.class);

        Configuration customScope = project.getConfigurations().create("libby");

        project.getPlugins().apply(JavaPlugin.class);
        project.getConfigurations().getByName(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME).extendsFrom(customScope);

        project.getTasks().register("libby", LibbyTask.class, customScope, project);
        project.getTasks().withType(ProcessResources.class).configureEach(task -> {
            task.dependsOn("libby");
        });
    }
}
