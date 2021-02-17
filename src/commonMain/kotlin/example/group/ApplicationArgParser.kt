package example.group

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
class ApplicationArgParser() : ArgParser(programName = "example") {
    init {
        subcommands(
        )
    }
}
