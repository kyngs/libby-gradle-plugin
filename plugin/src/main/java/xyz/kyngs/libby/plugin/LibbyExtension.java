package xyz.kyngs.libby.plugin;

import org.gradle.api.provider.Property;

import java.util.ArrayList;
import java.util.List;

public class LibbyExtension {
    private List<String> excludedDependencies = new ArrayList<>();

    public List<String> getExcludedDependencies() {
        return excludedDependencies;
    }

    /**
     * Add a dependency to exclude from the libby.json
     * <br>
     * The dependency is a regex matching the format "group:name:version" <br>
     * For example "org\\.company:library:.*" will exclude all versions of the library "library" from the group "org.company"
     *
     * @param dependency The dependency to exclude
     */
    public void excludeDependency(String dependency) {
        excludedDependencies.add(dependency);
    }
}