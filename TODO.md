* 
* lotcephak1-1/
* lothak-2/
* lotwav-3/
* 
* (done) overwrite property in nwt.json
* (done) chain compile, build, install etc
* initialize global config; find nwn
* need to figure out why genBlankStrings=false causes an issue because new objects will not have.  per model investigation
* ~/config file with everything from GlobalSettings
* option in nwt to skip existing files on unpack with list of files (regex) provided
* 

* (done) IfoModHak as array of strings
* (done) compile command - nwnRoot config variable
* (done) config preference to use .json5 extension
* (done) re-use compiled .ncs files
* (done) don't extract .nsc files by default for init
* (done) don't extract .ncs files when 'unpack' projects
* (done) don't extract erf file when 'unpack' projects
* (done) dont generate null in .json output
* (done) invoke script compiler
* (done) re-use compiled non .ncs files
* (done) when compile fails abort build

* (remove) only compiled changed .nss files (can't be done)
* (remove) config base location for source/target in nwt.json
* (remove) structIds

# more
* [ ] module description, nwt.json, erf.json
* [ ] show all fields with defaults
* [x] limit CResRef to 16 at most when parsing json
* [d] strip IndexId on json parse / replace with RefStructId
* [x] use renumber() to strip structId and replace with indexId
* [x] Con is changed with DLG is written
* [x] multi language text
* [x] nwt unpack
* [d] nwt pack
* [x] nwt build
* [x] nwt install
* [x] nwt compile
* [d] nwt test
* [x] nwt clean compile test install
* [x] conversation text, remove "stringRef" : 4294967295 default generation
* [d] shorthand for string/strings
* [d] ischild 0
* [d] test to unzip all module files and test dlg files within 