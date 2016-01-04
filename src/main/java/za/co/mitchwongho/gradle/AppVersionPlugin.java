package za.co.mitchwongho.gradle;

import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.LibraryPlugin;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.StopExecutionException;
import org.gradle.process.ExecResult;
import org.gradle.process.ExecSpec;

import java.io.ByteArrayOutputStream;

/**
 *
 */
public class AppVersionPlugin implements Plugin<Project> {

    final Logger log = Logging.getLogger(AppVersionPlugin.class);

    @Override
    public void apply(final Project project) {
        if (!hasAndroidPlugin(project)) {
            throw new StopExecutionException("No 'android' or 'android-library' plugin detected.");
        }

        final AppVersionExtension ext = project.getExtensions().create("appversion", AppVersionExtension.class);
        try {
            ext.setCode(getGitTags(project));
            ext.setName(getGitTagDescriptions(project));
        } catch (Throwable t) {
            //todo
        }
    }

    static Integer getGitTags(final Project project) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ExecResult res = project.exec(new Action<ExecSpec>() {
            @Override
            public void execute(final ExecSpec execSpec) {
                execSpec.commandLine("git", "tag");
                execSpec.setStandardOutput(baos);
            }
        });
        System.out.println(String.format("getGitTags:exit '%d'", res.getExitValue()));
        if (res.getExitValue() == 0) {
            final String str = baos.toString().trim();
            final int numTags = (str == null || str.length() == 0) ? 1 : str.split("\n").length;
            System.out.println(String.format("getGitTags 'num=%d'", numTags));
            return numTags;
        } else {
            return 1;
        }
    }

    static String getGitTagDescriptions(final Project project) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ExecResult res = project.exec(new Action<ExecSpec>() {
            @Override
            public void execute(final ExecSpec execSpec) {
                execSpec.setWorkingDir(project.getProjectDir());
                execSpec.commandLine("git", "describe", "--tags", "--long");
                execSpec.setStandardOutput(baos);
            }
        });
        System.out.println(String.format("getGitTagDescriptions:exit '%d'", res.getExitValue()));
        if (res.getExitValue() == 0) {
            final String str = baos.toString().trim();
            if (str == null || str.length() == 0) {
                return "1.0.0";
            } else {
                final String[] arr = str.split("-");
                // [0] - fullVersionTag
                // [1] - versionBuild
                // [2] - gitSHA
                System.out.println(String.format("getGitTagDescriptions '%s' {fullVersion=%s}", str, arr[0]));
                return arr[0];
            }
        } else {
            return "1.0.0";
        }
    }

    static boolean hasAndroidPlugin(final Project project) {
        return project.getPlugins().hasPlugin(AppPlugin.class)
                || project.getPlugins().hasPlugin(LibraryPlugin.class);
    }
}
