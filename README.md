# Neverwinter Toolkit - NWT
Neverwinter Toolkit (NWT) is a command-line tool for working with Neverwinter Nights module, hak, and related files.

#### Key Features
* Manage Neverwinter module projects as a traditional software development project using traditional source code methods (compile, build, etc.).
* Traditional source code management in version control systems (e.g. git).
* Convert artifacts to/from JSON source, optimized for human readability and editing.
* View, list and search NWN files.
* Creation of .mod and .hak files.

#### Why NWT?
Other projects already exist that perform similar functions, so why create a new project?   NWT takes a different approach to the generated JSON files, intending them to be human-editable, in addition to human-readable, so that the JSON looks more like traditional JSON instead of binary format represented in JSON.  The intent is to enable JSON Schema support.  Internally NWT uses a "static type" approach, where the "data model" of the files are programmed into the tool.   This approach allows for a simplified JSON representation, with the downside that the tool must be programmed to know about each field.  Internally NWT uses an annotated class-based approach.

#### Current Status As of March 2024
* Tested only on macOS
* Supports only Neverwinter Nights
* Tested only with Steam Neverwinter Nights EE - unlikely to work as-is with other versions
* The project is intended to support Windows, Linux and other variants of Neverwinter Nights 1.

#### Deep Thoughts
"If you are not embarrassed by the first version of your product, you've launched too late" - Reid Hoffman

## See Also
* [INSTALL.md](INSTALL.md)
* [USAGE.md](USAGE.md)
* [COMPILE.md](COMPILE.md)
* [DEVELOP.md](DEVELOP.md)
* [CHANGES.md](CHANGES.md)
* [LICENSE](LICENSE)

# Quickstart

### Initialize nwt configuration file
nwt uses a global configuration file.   It will locate the file in '$HOME/.config/nwt/nwt-config.json5'. This step Gmust be done once, and is really part of installing nwt itself

$ nwt config init

### Setup new project directory
$ mkdir dir1 

$ cd dir1

### Initialize project from module file
$ nwt init . /path/to/file.mod

### Compile, Build, and Install your artifact
$ nwt install

### Add a git repo
$ git init 

$ git add -A

$ git commit -m'Initial'

# More examples
| Command                                           | Description                                     |
|---------------------------------------------------|-------------------------------------------------|
| nwt install                                       | compile, build, and install                     |
| nwt i                                             | same as 'install'                               |
| nwt init &lt;dir&gt; &lt;.mod or .hak file&gt;    | generate nwt.json5, .gitignore, and unpack      |
| nwt init -n &lt;dir&gt; &lt;.mod or .hak file&gt; | generate nwt.json5, .gitignore                  |
| nwt unpack                                        | unpack from module/hak file nwt.json5 "source"  |
| nwt clean install                                 | clean then compile, build, and install          |
| nwt c i                                           | same as 'clean install'                         |
| nwt clean compile build install                   | doesn't make sense, just use 'clean install'    |
| nwt clean build                                   | clean then compile, build but do not install    |
| nwt clean                                         | just clean                                      |
| nwt c                                             | same as 'clean'                                 |
| nwt compile                                       | just compile .nss                               |
| nwt o                                             | same as 'compile'                               |
| nwt compile -f                                    | force recompile of all .nss files               |
| nwt compile -i                                    | See note **Incremental Compile** below          |
| nwt build                                         | compile then build                              |
| nwt b                                             | same as 'build'                                 |
| nwt config                                        | show nwt global config and config file location |
| nwt config init                                   | initialize or re-initialize nwt-config.json5    |
| nwt -h                                            | show top-level help                             |
| nwt init -h                                       | show help for init command                      |
| nwt install -f                                    | Force compile of all .nss files                 |
| nwt install -i                                    | See note **Incremental Compile** below          |
| nwt -h                                            | help nwt                                        |
| nwt install -h                                    | help for install command                        |
| nwt erf -h                                        | help for erf command                            |
| nwt erf l -h                                      | help for sub-command l of erf command           |

### Incremental Compile
Incremental compile of .nss files.  BE CAREFUL.  This will only compile changed files.  However, .nss files can include other files, and if an included file has changed all files that include that file must also be compiled, and -i will fail to detect this.  Only use this when you are certain there are no changed included files.

# About NWT
* Supports building .mod or .hak files; does not support creation of .bif, .key files.
* Able to read, extract and create ERF files: .mod, .hak, .nwm
* Able to read, write, and convert to/from JSON for [GFF files](USAGE.md#gff-file-types)
* Able to read and extract files: .bif, .key
* Uses JSON5 file format with .json5 file extension by default.   JSON5 has many quality of life improvements including comments and comma-terminated arrays.  Using the actual .json5 file extension lets your editor provide proper support.
* NWT uses smart incremental compilation for both .nss and .json5 files.   This results in fast build times when only a small number of files have changed.  A large module file of size 49 megabytes made up of 7,800 files can complete an incremental build and install in under 3 seconds on a Macbook Pro M1.
* At this time only one output artifact is supported per project (e.g. one .mod file or one .hak file).   Support for multiple artifacts per project may be added in the future.
* NWT has been extensively tested, and has automated tests included.
* By default, the init command will generate nwt.json5 file to avoid overwriting your original input module.  You can change this by modifying the nwt.json5 file.
* The sub-commands clean, compile, build, install take their inspiration from Maven and Gradle.
* The official Neverwinter Nights Aurora Toolset (nwtoolset.exe) has been observed adding extra fields to .mod files, specifically prepending the lowercase letter "x" to an existing field, and changing the type (e.g. from BYTE to WORD).  These expanded x fields have not all been added to the internal model, and the toolkit will provide a warning when they are encountered (or any unexpected field is encountered).


# Tips and Tricks
* When importing some existing modules (nwn init), the module may contain .ncs files that are not produced during the compile phase.  Use 'nwt diff' compare original module with built module to detect this.


