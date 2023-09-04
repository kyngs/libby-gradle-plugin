package xyz.kyngs.libby.plugin;

import com.github.jengelman.gradle.plugins.shadow.internal.AbstractDependencyFilter;
import com.github.jengelman.gradle.plugins.shadow.relocation.Relocator;
import com.github.jengelman.gradle.plugins.shadow.relocation.SimpleRelocator;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.specs.Spec;

import java.util.ArrayList;
import java.util.List;

public class ShadowPluginIntegration {
    protected static List<LibbyTask.Relocation> extractShadowJarRelocations(Project project) {
        var task = project.getTasks().withType(ShadowJar.class).named("shadowJar").get();

        var relocations = new ArrayList<LibbyTask.Relocation>();

        for (Relocator relocator : task.getRelocators()) {
            if (relocator instanceof SimpleRelocator simpleRelocator) {
                relocations.add(new LibbyTask.Relocation(simpleRelocator.getPattern(), simpleRelocator.getShadedPattern()));
            }
        }

        return relocations;
    }
}
