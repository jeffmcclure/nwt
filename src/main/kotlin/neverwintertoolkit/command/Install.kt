package neverwintertoolkit.command

import neverwintertoolkit.Nwt
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.fileSize
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.name
import kotlin.io.path.notExists

class Install(val nwtJson: Path, val dir: Path = nwtJson.parent, val installCommand: InstallCommand = InstallCommand()) {

    val targets: List<Nwt> by lazy { Nwt.parseJson(nwtJson) }

    fun install() {

        targets.forEach { nwt ->
            val installPath = nwt.targetPath
            if (!nwt.overwrite && !installCommand.overwriteOption && Files.exists(installPath)) throw RuntimeException("Target file already exists, use --overwrite to overwrite existing target $installPath")
            val compiledArtifact = Paths.get("target").resolve(installPath.name)
            if (installPath.exists() && compiledArtifact.getLastModifiedTime() == installPath.getLastModifiedTime() &&
                compiledArtifact.fileSize() == installPath.fileSize()
            ) {
                installCommand.logInfo { "No change" }
            } else {
                installCommand.logInfo { "Installing $installPath..." }
                if (installPath.parent.notExists())
                    Files.createDirectories(installPath.parent)
                Files.copy(compiledArtifact, installPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES)
            }
        }
    }

}
