
EXIFutils V2.7.4 (29-November-2006)

  Web:   http://www.hugsan.com/EXIFutils
  Email: support@hugsan.com

  Copyright (c) 2000-2006 Hugsan Pty. Ltd. All Rights Reserved.
                                                                          
  See EXIFutilsLicense.rtf for conditions of use of this software.

PACKAGE CONTENTS
   
  readme-win.txt               - the file you are reading now
  EXIFutilsLicense.rtf         - license conditions for using this software
  exiflist.exe                 - exiflist executable
  exifdate.exe                 - exifdate executable
  exifcopy.exe                 - exifcopy executable
  exifedit.exe                 - exifedit executable
  exiffile.exe                 - exiffile executable
  exifkey.exe                  - exifkey executable

  docs\pdf                     - User Documenation - PDF format

INSTALLING THE EXIF UTILITIES

To install the EXIFutils on a Windows system, do the following:

1. Download the EXIFutils installation program into a directory on
   your hard disk.

2. If your system requires you to log on, then log on as a user 
   who has sufficient privilege to install software.

3. Double-click on the kit to start the installation process.

4. Follow the instructions displayed by the installation process.
   If you are installing a Full release and an earlier version of 
   EXIFutils is already installed, the installation program will 
   offer to de-install the earlier version. It is recommended that 
   you accept this option.

5. If asked to do so by the installation program, reboot your system 
   (this is necessary to add the EXIFutils to you PATH so that Windows
   knows where to find them)

   NOTE: For Windows2000 systems it is not necessary to reboot your
   system. Logging off then logging on again is sufficient to complete
   the EXIFutils installation process.

6. If you have purchased a Full Use license for the EXIFutils, run
   exifkey to install the license: 
   a. Open an MS-DOS Command Prompt window.  
   b. Run exifkey to install the license, eg:

      exifkey /k 1234567890123 /u "John Smith" /e john@smith.com

The license key, user name, and email address must be entered exactly
as shown in the registration information you received.

USING EXIF UTILITIES
 
  There utilities are console programs that are run in an MS-DOS window.
  See the User Guide for a complete description of how to use the 
  EXIFutils.
  
  Basic usage information can be displayed using the /h option on all
  commands, eg:
  1) Open a DOS window (Start->Programs->MS-DOS prompt)
  2) Enter the program name with only the /h option. eg.

     exiflist /h | more


REPORTING PROBLEMS

  Please report any problems to support@hugsan.com

VERSION HISTORY

V2.7.4 
 - Fix bug in subtraction of dates/times in exifdate, that caused
   an invalid date to be calculated.
 - Fix bug in exifcopy which caused the same file to be processed
   multiple times on systems with FAT32 file system.

V2.7.3
 - Fix bug that caused the following command to report an error.
   exifedit /a "date-taken=[ip-date][ip-time]" file.jpg
   This now works correctly.
 - Fix crash that occured when deleting IPTC fields when there are no
   IPTC caption fields (but there are other IPTC fields).
 - Add cn-0093 field.
 - Increase accuracy of GPS longitude minute and second values from 2 to 3 
   decimal places. 

V2.7.2
 - Fix bug in calculation/validation of EXIF string field length. An extra
   '\0' byte was included at the end of the string field, and validation
   of string fields with specific length restrictions was incorrect.

V2.7.1
 - Fix bug which caused ERROR_INVALID_PARAMETER to be displayed when 
   a zero length IPTC fields was encoutered, as is sometimes the case with 
   Image URL fields inserted by exifer. (Windows Only)

V2.7
 New Features
 - Add "read only" support for EXIF fields in Encapsulated Postscript (.eps)
   files.
 - Add support for Ricoh Maker Note fields
 - Add read only support for Ricoh RMETA Custom Fields (created by Ricoh
   Ricoh Pro G3 GPS-capable camera).
 - Improved support for non-ASCII character sets:
   - Add support for viewing and editing Windows XP specific UNICODE fields.
   - Added /u option to exifedit to specify that EXIF Comment fields are to 
     be stored in UNICODE (previous only ASCII comments were supported).
   - The /p option has been added to set the character set used to interpret
     EXIF and IPTC text fields. Environment variables can also be used to 
     specify characters sets.
   - Added /i option to exiflist, to display the character sets being used.
 - Add support for XP Digital Image Pro Tags field (0x4747)
 - Increase max length of ip-keyword field from 32 to 1999 characters.
 - Add list of defined values for IPTC Coded Character Set field (1:90),
   nickname ip1-char-set.
 - If the denominator of the exp-time value is not a whole number to two
   decimal places, then display it's value to two decimal places. 
 Bug Fixes
 - Fix bug where two fields had the same nickname (n3-color-mode). Second
   occurence of the field was renamed to n3-color-mode2.
 - Make handling of invalid 'next IFD' offset pointers more robust. In 
   previous versions invalid offset pointers cause a "too many format errors"
   message and resulted in incomplete EXIF data.
 - Fix bug in handling of TIFF strip offsets and lengths with datatype SHORT.
 - Fix bug in exiflist -t option that caused extracted thumbnail to be
   incorrect for some Nikon images. The problem occured when there were 
   two thumbnails in the image, one of which was in the Nikon Maker note.
 - Rename "Nikon NEF Thumbnail" IFD to "Nikon Thumbnail" as this IFD can
   also appear in JPEG files.
 - Fix bug in editing files containing Thumbnails embedded in Nikon Maker Notes
 - Fix bug that caused incorrect values to be set when IPTC values were 
   being both set and used as template values in the same exifedit command.
 - Improve validation of command arguments containing @ file input as
   invalid values could cause the program to crash.
 - Allow exif-ver field to be set to values higher than "0220" as images
   files using version "0221" are now available.

V2.6.2
 - Fix bug in handling of thumbnails when editing JPG files. exifedit
   was incorrectly copying the thumbnail from the original file.
 - Fix bug in exifedit on MacOS X systems which caused files in a directory
   to be processed multiple times.

V2.6.1
 - Increase accuracy of GPS minute and second values from 2 to 3 
   decimal places. 
 - Fix bug in handling of thumbnails in Nikon NEF raw files
 - Fix bug in handling of Panasonic Maker Notes, and Maker Notes in Canon 
   .cr2 Raw files (fields following the Maker Note were sometimes ignored)
 - Field add lists in exifedit -a option were being truncated to 2048 bytes
   when being read from a file. This restriction has been removed by
   dynamically allocating sufficient memory to store the entire file.
 - Fix bug in handling of thumbnails in Canon .cr2 files. This bug may also
   affect some TIFF and Nikon .nef files.
 - Renamed Canon and Olympus Make Note IFD names so that they no longer 
   refer to individual camera models.

V2.6.0
 - New file formats supported
   - Canon Raw (.CRW and .CR2) files now supported by exiflist, exifcopy,
     and exiffile.
 - Improved support for model-specific fields (Maker Notes)
   - Panasonic Maker Notes now supported
   - Konica Minolta branded cameras now supported
   - Add support for Pentax Maker Note Lens Id field (px-lens-id).
 - Can now reference individual parts of the Date Taken EXIF field using
   the following new nicknames:
   - yyyy - 4 digit year
   - yy   - 2 digit year
   - mm   - 2 digit month
   - mmm  - 3 letter month (eg. Jan)
   - hr   - 2 digit hour (24 hour clock)
   - min  - 2 digit minutes
   - sec  - 2 digit seconds
 - Improved GPS support
    - GPS seconds values to be specified to 2 decimal places (previously
      only integer whole numbers of seconds were accepted).
 - Add support for more iptc fields:
       ------- Field Name ------ -Attr- -Tag- ----- Description ----------
     - ip-local-caption           IRWS  2.121 Local Caption
     - ip-job-id                  IRW-  2.184 Jod Id
     - ip-master-doc-id           IRW-  2.185 Master Document Id
     - ip-short-doc-id            IRW-  2.186 Short Document Id
     - ip-unique-doc-id           IRW-  2.187 Unique Document Id
     - ip-owner-id                IRW-  2.188 Owner Id
     - ip-class-state             IRW-  2.225 Classification State
     - ip-sim-index               IRW-  2.228 Similarity Index
     - ip-doc-notes               IRW-  2.230 Document Notes
     - ip-doc-history             IRW-  2.231 Document History
     - ip-exif-info               IRW-  2.232 Camera EXIF Information
 - Binary fields can now be edited.
 - Add -z option (trace meta data format)
 - Bug fixes
   - Ensure padding bytes in IPTC data are zeroed, as Photoshop does not accept
     non-zero padding.
   - Fix error in exifdate usage text
   - Fix detection of EXIF data in Minolta raw (.MRF) files from Minolta 7D SLR
   - Fix double processing of renamed files in exiffile when run on WindowsXP.
   - Fix handling of EXIF thumbnails in TIFF, NEF, and .DCR files.
   - Fix bug in detection of EXIF data in Fujifilm .RAF files.

V2.5.8
 - Fix bug in detection of Maker Note for Nikon D1H with Firmare Version 1.1
 - Fix bug that caused an exception when IPTC data is present both in the
   EXIF APP1 data block and also in the APP13 data block. The IPTC data in 
   the APP13 data block is a now ssumed to supercede any IPTC data present
   in the EXIF data block.
 - Previous version of exiflist ignore image files that do not contain
   any meta data, i.e. no output was produced for these files. exiflist will
   now display file related information for these images even if no meta
   data exists.
 - exifedit now accepts EXIF format dates with timezone appended, eg:
   "2004:04:01 12:23:18 +0800". This allows the user to now set an IPTC
   time field based on an EXIF time, and include a timezone, eg:

   exifedit /a "ip-time=[date-taken] +0800" myfile.jpg

V2.5.7
 - Add support for more IPTC fields: 
       ------- Field Name ------ -Attr- -Tag- ----- Description ----------
     - ip-obj-type-ref            IRWS  2.003 Object Type Reference
     - ip-obj-attr-ref            IRWS  2.004 Object Attribute Reference
     - ip-editor-update           IRWS  2.008 Editorial Update
     - ip-subj-ref                IRWS  2.012 Subject Reference
     - ip-cont-loc-code           IRWS  2.026 Content Location Code
     - ip-cont-loc-name           IRWS  2.027 Content Location Name
     - ip-action-advised          IRWS  2.042 Action Advised
     - ip-ref-service             IRWS  2.045 Reference Service Ident.
     - ip-ref-date                IRWS  2.047 Reference Date
     - ip-ref-env-num             IRWS  2.050 Reference Envelope Number
     - ip-digi-date               IRWS  2.062 Digital Creation Date
     - ip-digi-time               IRWS  2.063 Digital Creation Time
     - ip-image-orient            IRWS  2.131 Image Orientation
     - ip-audio-type              IRWS  2.150 Audio Type
     - ip-audio-samp-rate         IRWS  2.151 Audio Sampling Rate (Hz)
     - ip-audio-samp-res          IRWS  2.152 Audio Sampling Resolution
     - ip-audio-duration          IRWS  2.153 Audio Duration
     - ip-audio-outcue            IRWS  2.154 Audio Outcue
     - ip-preview-format          IRWS  2.200 Preview File Format
 - In exiffile, if the name to which a file is being renamed already exists
   (possibly as the results of a previous rename) then the existing file was
   overwritten. exifile now detects these name clashes and adds a suffix to 
   the renamed file to make the name unique.
 - Fix bug that caused exifedit to create an invalid JPEG file if the APPx
   blocks were not in ascending order in the original file.
 - exiflist -l xxx showed incorrect max string value lengths for IPTC fields.
   The values displayed were one byte less that the actual maximim value 
   (exifedit enforced the correct length).
 - Miscellaneous corrections in the User Documentation.
 - Accept '.' as separator in dates and times, eg 2004.01.01 is now a 
   valid date.
 - Accept complete time values that have trailing separator, eg "12:30:  "

V2.5.6
 - Add support for new Olympus Maker Note format
 - Treat Maker Note from Canon Camcorders as opaque data as the format is
   unknown.
 - IPTC string field values that contained a NULL terminator character where
   not being handled correctly. For repeating fields (ipkeyword, ip-suppcat)
   this resulted in only the first instance of the field being displayed.

V2.5.5
 - Fix bug in handling of Minolta and Canon Maker Note sub-fields.

V2.5.4
 - Fix bug in detection of Nikon Maker note type (found on D70 model), which
   caused a buffer overun error.

V2.5.3
 - Fix bug in handling of non-image files that caused a Bus Error.

V2.5.2
 - Clean up field definition details.
 - Still apply edit actions even if errors are detected when extracting the
   EXIF data. This is neccessary so that you can do edit to possibly correct
   the EXIF format errors.

V2.5.1
 - Fix buffer exhaustion that occured when processing large Kodak .dcr files.

V2.5.0
 - exifedit
   � Edit IPTC fields, and copy field values between IPTC fields and EXIF 
     fields (and vice versa)
   � exifedit can now process all files in a directory tree. Previously 
     could only process all files in a single directory.
   � Setting date/time field made simpler. Previously you had to specify 
     the date time in exactly the right format. Now exifedit will accept a
     number of different date formats and will automatically convert to the
     required format. Also, if a date is specified when I date/time is 
     required, the time will automatically be set to 00:00:00.
   � EXIF data/time values can be assigned to IPTC date/time fields (and 
     vice versa) and the value will automatically be converted to the correct
     formati.
   . can now individually remove all EXIF, IPTC, or Flashpix audio data from
     an image
   . In field/value lists, field values containing spaces no longer need to
     be enclosed in quotes. For example, the following is now valid:

         exifedit /a "date-taken=2004:03:31 12:13:00" myphoto.jpg

     in previous versions an additional set of quotes was required:

     exifedit /a "description=\"2004:03:31 12:13:00\"" myphoto.jpg

   � /j option added. Specifies the separator character to use between 
     repeating IPTC fields (eg ip-keyword and ip-suppcat)

 - exiflist
   � Limit on size of template file removed (previously the maximum was 
     10,000 bytes; it si now limited only by available memory).
   � IPTC fields now included in .exi files created with the /e option
   � /n option added. Specifies the character with which to replace new line 
     characters in output values.
   � /t /w /e can now be used in conjunction with /r to extract thumbnails, 
     audio data, and meta data from all files in a directory tree.
   � /j option added. Specifies the separator character to use between 
     repeating IPTC fields (eg ip-keyword and ip-suppcat)

 - exifcopy
   � When copying between JPEG files, Flashpix and IPTC fields now copied 
     (previously only EXIF data was copied).

 - Miscellaneous
   � Minor corrections to Minolta Maker Note fields
   � Add TIFF Document Storage and Retrieval tags
   � Pentax Maker Notes now supported.
   � Maker notes formats for major brands now automatically detected

 - Bug Fixes
   � Editing TIF files that contained a single strip thumbnail caused an
     invalid TIF file to be generated
   � GPS latitude and longitude values were incorrectly being treated as 
     signed values instead of unsigned.
   � Windows XP related issues
     o WINDIR not defined on all WindowsXP systems. Use SYSTEMROOT instead.
     o When processing all files in a directory using exifedit or exifcopy,
       on some systems the command would processed generated backup files 
       in a directory

V2.4.11
  o Fix bug in handling of IPTC fields in TIF files. If the data type of the 
    field was UNSIGNED CHAR, then the IPTC fields were not extracted.

V2.4.10
  o Handle format change in Nikon D1 Maker Note.

V2.4.9
  The following problems have been fixed:
  o General
    o (Windows only) File/path names longer than 100 bytes cause EXIFutils 
      commands to abort. The maximum path/file name length has been increased
      to 256.
    o Add Maker Note support for Canon DIGITAL IXUS 400
  o exifcopy
    o When copying all EXIF data from some Minolta Raw (.MRW) files to a
      JPEG file, corrupt JPG was produced. This was due to the EXIF data in 
      some .MRW files being long than is allowed by the EXIF spec. 
      exifcopy now prints an error does not attempt to copy .MRW EXIF data if 
      it is too long.
  o exifedit
    o When using the -s option to update a large number of files in a 
      directory, the following error would occur:
      Warning: Field table full. Some edit actions may not have been performed.
  o exiflist
    o The '-e .' would produce an incomplete .exi file when extracting from
      some Minolta Raw (.MRW) files. This was due to the EXIF data in some
      .MRW files being long than is allowed by the EXIF spec. 
      exiflist now prints an error does not attempt to extract .MRW EXIF 
      data if it is too long.
      
V2.4.8
  New Features
  o Additional file types supported
    o Minolta Raw (.MRW) files: All EXIF fields can be listed. The entire i
      EXIF data block can be copied into a JPEG file or into an EXIF .EXI file.
      Selected EXIF fields can be copied to any other image file type. Minolta
      Raw files cannot be editied. 
    o Fujifilm Raw (.RAF) files: All EXIF fields can be listed. Selected EXIF 
      fields can be copied to any other image file type. Fujifilm Raw files 
      cannot be editied.
    o EXIF .EXI files: .EXI files are used to backup EXIF information from 
      other image file for later return after the image has been edited. Using
      the exiflist /e option, all EXIF data can be extracted from a .JPG, .MRW,
      or .RAF file and stored in a .EXI file. EXIF information in a .EXI file 
      can then be copied into any other image file using exifcopy. The .EXI 
      files created by EXIFutils are compatible with those created by the 
      exifer application (www.friedemann-schmidt.com/software/exifer).
  o Improved Maker Note support
    o Support for Canon Maker Note fields greatly improved. In earlier versions
      of EXIFutils many Canon Maker note values where combined into two fields
      "Settings 1" and "Settings 4". All Canon Maker note fields can now be 
      individually selected and displayed.
    o Most Minolta Maker Note fields are now interpreted (previously the 
      meaning of most fields was unknown).
    o Maker Notes for the following cameras are now supported:
      -	Canon Powershot S45, S50, A300, A60 ,A70 , G3, G5 ,S400, SD100, 
        100D, Digital Rebel.
      -	Casio EX-Z3, EX-S3, QV-5700, QV-R40
      -	Fujifilm FinePix A205, A210, A310, F410, S5000
      - Minolta DiMAGE 7Hi, Xi, Xt, Z1, A1, F300
      -	Nikon E3100, E5400, SQ
      -	Olympus C750UZ, X200, D560Z, C350Z, u10D, S300D, u300D, u20D, S400D, 
        u400D. 
    o More IPTC fields now supported. 56 more IPTC fields can are now be i
      displayed, include fields from Dataset 1 (Addressing), Dataset 2 
      (Editorial), and Dataset 7 (Subfile Size).
    o Additional image file attributes available as fields for use in output 
      and templates:
      -	file-date-mod		- Date the file was last modified
      -	file-date-created	- Date the file was created (Windows only)
      - file-name-exi		- Default name of the .exi file created by 
                                  exiflist -e option.
    o The User Guide has been split into three separate documents:
      -	A Getting Started Guide, which describes concepts common to all 
        EXIFutils commands, and explains common scenarios in which EXIFutils 
        can be used.
      -	A Command Reference Manual, which describes in detail the features 
        provided by each EXIFutils command.
      -	A Field Reference Guide, which list all EXIF, IPTC, and file attributes
        fields that can be used by EXIFutils.

  The following problems have been fixed:
  o General
    o Absolute path name in thumbnail template did not work on Windows ("C:\" 
      was translated to"C-\").
    o Handling of incorrectly formatted IPTC fields made more fault tollerant.
    o Spurious characters appended to some character strings when displayed.
    o On Windows, the /? option was not being recognized. It is now produces 
      usage information (the same as /h). 
  o exifcopy
    o spaces in file names not handled when  /f option used
  o exifdate
    o When adding or subtracting a date/time value, the addition/subtraction 
      was applied to the date-modified field twice.
  o exifedit
    o When adding a large thumbnail image using the /t a  option, a corrupt 
      EXIF data block was created if the thumbnail image was too large to fit
      in the EXIF block. An error message is now displayed if the thumbnail is
      too large.
    o When adding EXIF data to a JPG that contained an invalid EXIF data
      block, a corrupt JPG file was produced
  o exifkey
    o A Sementation Fault occurred if the license file could not be read.
    o Trailing space characters in the username or email address caused 
      exifkey to hang.
  o exiflist
    o The ip-image-type and ip-lang-id field were not being displayed even if 
      they were present.
    o When using list output (/o l option) field value strings containing 
      the separator character were not being quoted

V2.3.2
 - General
   . IPTC field values longer than maximum allowed field length were being 
     treated as if the field was absent. Long field values are now accepted.
   . ip-time field (supported by DigitalPro) was not being handled.

V2.3.1
 - General
   . Canon Maker Note fields for PowerShot S40 were being translated 
     incorrectly when EXIF data used Motorola byte ordering.

V2.3
  New Features

  - General
    . EXIF 2.2 fields are now supported
    . IPTC (International Press Telecommunications Council) fields can 
      now be displayed.
    . Added Maker Note support for the following cameras: 
      o Olympus C5050Z,  
      o Fujifilm FinePix F401,  
      o Minolta DiMAGE F100,  
      o Nikon D1,  
      o Canon G1,  
      o Canon G2 
    . Improved support for Nikon Type 3 Maker Notes. The following 
      additional Maker Note fields now interpretted:
      o Lens Type
      o Flash Used
      o Bracketing
      o Flash Compensation
      o Exposure Difference
      o AF Focus Position
      o Lighting Type
    . Additional file attribute nicknames are available:
      o file-ext (file name extension ) 
      o file-base (file base name)  
      o file-name-thumb (file name of extracted thumbnail) 
      o file-name-audio (file name of extracted audio stream) 
      o file-name-backup (name of backup file to be created)
    . Nicknames can now be entered in both upper and lower case 
  exiflist
    . In addition to predefine output formats, exiflist now supports 
      output based on a user-defined template file.
    . When extracting the embedded thumbnail image using the /t option, 
      the user can now specify the name of the extracted file, and the 
      directory into which it is written.
    . When extracting the embedded audio stream using the /w option, the 
      user can now specify the name of the extracted file, and the 
      directory into which it is written.
  exifedit
    . The value to which a field is set can now be specified as a template
      that includes the values of other fields.
  exiffile
    . Added /n option, which renames images files based on the values of 
      other EXIF, IPTC, and field attribute nicknames.
  exifcopy
    . It is now possible to copy individual fields from one JPG file to 
      another JPG file using the /f option

  The following problems have been fixed:
  - General
    . \r not handled correctly in string fields
    . Temporary files were sometimes created in the current 
      working directory in which the program was run, instead 
      of being created in the same dir as the file being 
      edited/copied.
    . Some Olympus and Minolta Maker Note fields definitions 
      were incorrect, causing warning messages to be 
      erroneously displayed.
    . Maker notes created by Nikon D1X cameras with a firmware version
      of V1.10 were not handled correctly.
  - exiflist
    . String values were not wrapped correctly when last 
      character on the line was white space.
    . Random text appended to the end of some strings output values.
    . file-name nickname interpreted as file-name-full in SQL output
    . Directory name incorrectly included in the file-name field when
      a path was included on command line
    . exiflist reported shutter speeds written by exifedit incorrectly
  - exifedit
    . Generated Backup file names and thumbnail file names not correct 
      when dot '.' was the first character of the file name
    . Multiple APP1 markers in an JPG file caused EXIFEDIT to generate 
      a corrupt JPG file.
    . exifedit incorrectly formats FUJIFILM Maker Notes field for some 
      Fujifilm models.
    . Temporary thumbnail files were not always deleted
  - exifcopy
    . File names starting with a dash '-' not handled correctly

V2.2.4

  EXIFEDIT
  1. Fix bug in /e option. Non-EXIF data was being deleted.

V2.2.3

  EXIFLIST
  1. Fix bug in extraction of .wav files. Long audio files where
     being truncated.

V2.2.2

  GENERAL
  1. Added Maker Note support for the following makes/models:
    . Canon PowerShot S20
    . Canon PowerShot Pro90 IS
    . Casio QV-3000EX
    . Casio QV-3EX
    . Casio QV-4000
    . Epson PhotoPC 3000Z
    . Epson PhotoPC 3100Z
    . Fujifilm FinePix F601 ZOOM
    . Fujifilm FinePix S602 ZOOM
    . Fujifilm FinePix 4700 ZOOM
    . Fujifilm FinePix 4800 ZOOM
    . Fujifilm FinePix 4900 ZOOM
    . Fujifilm FinePix 6800 ZOOM
    . Fujifilm FinePix 6900 ZOOM
    . Fujifilm FinePix S2 Pro
    . Minolta DiMAGE 5
    . Minolta DiMAGE 7i
    . Nikon E880
    . Nikon E885
    . Nikon E995
    . Nikon E5000
    . Nikon E4500
    . Nikon E5700
    . Nikon D1H
    . Nikon D100
    . Olympus C3000Z
    . Olympus C300Z
    . Olympus D550Z
    . Olympus C3030Z
    . Olympus C3040Z
    . Olympus C3100Z
    . Olympus C3020Z
    . Olympus C4040Z
    . Olympus C40Z
    . Olympus C720UZ
    . Olympus E-10

  2. Fix minor EXIF specification conformance issues:
     a. exiflist assumed first IFD always started at offset 8. 
        It now correctly determines the offset to the EXIF data.
     b. User Comment field was sometimes not placed in field tag 
        order within the EXIF data
     c. Related Sound File field was incorrectly defined as variable
        length. Correct definition is as a 12 character fixed length field.
     d. EXIF data type 13 (IFD offset) now supported.
  3. Add definitions for Windows XP specific fields.

  EXIFEDIT
  1. exifedit can now add EXIF data to an image file that did not contain
     EXIF data (previously exifcopy had to be used to add EXIF data to a 
     file before exifedit could be used to edit the fields). If exifedit 
     is used with the '/a', '/s', or '/t a' options, and the file does not 
     contain any EXIF data, then exifedit will create a basic EXIF structure
      before adding the specified fields.
   2. Added /e option to remove all EXIF data from the image file (i.e. all 
      APP1 EXIF data blocks and all APP2 FlashPix Extension data blocks 
      will be deleted).
   3. Add /t a,@filename option to replace thumbnail image with a 
      user-supplied JPEG thumbnail image.

  EXIFLIST
  1. Added /w option to extract embedded EXIF audio data into a separate
     .wav audio file.
  2. Added /k option to allow user to specify an alternative quote character
     for /o ln format output.
  3. Fixed bugs in escaping of quote characters: 
     a. In '/o ln' format output, double quotes characters (") were not 
        preceded by the escape character when they occurred within field values  
     b. In SQL format output, an escape character did not precede single quote 
        characters when they occurred within field values. 
  4. In SQL output format, string field values should be surrounded by
     quote characters. For some fields the quotes were ommitted.

V2.1.1

  GENERAL
    1) Bug fix: In EXIFEDIT and EXIFCOPY, a maximum of 500 files can be 
                processed in one invocation when a backup of each modified
                file is being kept. This limit was incorrectly retricting
                the number of file processes when no back files were being
                kept (i.e. when using the /b option). 

                The number of files that can be processed in one invocation
                EXIFEDIT or EXIFCOPY with the /b option specified is now 
                unlimited. (PR101).

  EXIFEDIT
    2) Bug fix: Some 3rd party EXIF viewers did not display all EXIF
                information after the thumbnail image was regenerated
                by EXIFEDIT. This problem has been fixed. (PR103)

  EXIFCOPY
    3) Bug fix: If an attempt was made to copy EXIF data into a file
                that already contained EXIF data, and the /o option
                was not specified, then the error message displayed 
                incorrectly suggested the use of the /f option to 
                force overwriting of the EXIF data. The message should
                suggest using the /o option. (PR102)

V2.1

  GENERAL
    1) Bug fix: When no valid license was installed, the 'licensed user'
                display was corrupt.

  EXIFEDIT
    1) Added improved thumbnail generation. The /t option can now be 
       specified in three different forms:
       /t a           - generate a thumbnail that is 160x120 pixels
       /t a,size      - generate a thumbnail that has its longest
                        dimension 'size' pixels long, with the length
                        of the shorter side scaled to maintain the image
                        aspect ratio, eg /t a,300
       /t a,wwwxhhh   - generate a thumbnail that is www pixels wide 
                        and hhh pixels high, eg /t a,300x200

V2.0

  GENERAL
    1) Added support for TIF files. Other file formats based on the TIFF 
       standard should also work, but the only ones I have tested is the
       Nikon .nef and Kodak .dcr file formats.
    2) Can copy/merge fields between JPG and TIFF files or between two
       TIFF files.
    3) New utility EXIFFILE that sets the JPG or TIF file's last modified date
       to the date the photo was taken.
    4) Increase maximum number of fields that can be specified (for
       listing or edit) to 200 (was previously 20).
    5) Proper installation process added (Windows version only)
    6) Use Guide now included.
    7) Add support for more Maker Note fields:
       - Olympus E-20, E20-N, E-20P
       - Canon PowerShot S40
       - Canon PowerShot A20
       - Nikon D1X
       - Epson PhotoPC 850Z
    8) Windows version now accepts UNIX-style command options i.e. option 
       parameters can start with '-' instead of '/'. For example, both of the
       following are now valid in the Windows version:
            exiflist /o l /f file-name,date-taken .
            exiflist -o l -f file-name,date-taken .

  EXIFLIST
    1) Added support for more Canon Make Note fields (Quality, Digital
       Zoom, Focus Type, Flash Activity).
    2) Unknown fields are now printed in the IFD in which they are found
       rather than being listed together at the end.
    3) File Source and Scene Type now interpretted correctly.   
    4) Added the /o lf and /o ln options 
    5) /l option now requires a suboption. Valid values are '/l n' (list 
       field names / this is what /l alone did in V1.5), '/l f' (print a 
       full list of fields and details of valid field values to be
       used when editing, '/l field-name-list' (similar to '/l f' but
       only prints details for selected fields.
    6) Can now use wildcards when specifying fields in the /f option.
    7) Can now specify /f exif-common to select all common EXIF fields
    8) Fix bug which caused an extra comma to be included at the end of
       the heading line when /o lh option used.

  EXIFEDIT 
    1) Can now add/replace/delete most fields. See '/l f' command for 
       a complete list of the fields that are now editable.
    2) Can automatically adjust the width and height fields to match 
       the actual image size
    3) Fix buffer overrun problem if field value longer than maximum
    4) Bug fix: Editing EXIF data that contained GPS information caused
       invalid EXIF data to be written.
    5) Added '/t a' to regenerate thumbnails

  EXIFCOPY
    1) /m option will now accept a match length of zero, which means
       that all destination files will match.
    2) Can now copy selected fields to/from tiff files.
    3) Incompatibilities:
       - /f changed to /o
       - backup files now suffixed with -be instead of -nx

V1.5

  GENERAL
    1)  Fixed bug in handling of EXIF data larger than 2KB.

  EXIFLIST
    1)  Added /t option, which extract the thumbnail image from
        the EXIF data into a separate file.
    2) Added support for Global Positioning System (GPS) fields
    3) Added support for reading field list (/f option) from 
       standard input 
    4) Added support for the following Maker Note Fields
       - Olympus C2500L
       - Fujifilm
       - Minolta DiMAGE 7 (basic support only) 
       - Nikon D1X        (basic support only)
       "Basic support" means that the field values are displayed,
       but I don't know what they mean ;^)

  EXIFEDIT
    1) Added support for reading field lists (/a /r options) from 
       standard input 
    2) "/t" option changed to "/t r" in preparation for adding more
       thumbnail actions (add thumbnail is planned)
    3) Fixed bug in generation of Maker Note fields. This bug caused
       some EXIF display programs to fail to displaying Make Note 
       fields (see below for more details).
    4) Fixed bug in order of generated EXIF fields. 
       The EXIF structure written by EXIFEDIT did not conform to the
       EXIF standard in that fields were not order in ascending order
       by field tag. As far as I know this error did not cause any 
       problems, but it has now been corrected so that the fields are
       written in the order required by the EXIF Specfication.

  EXIFEDIT Make Note Bug
    There was a bug in the generation of Maker Note fields which caused
    the Maker Note tag to have a data type of UNSIGNED LONG and a length
    of 1. It should have had a data type of UNDEFINED and the length set
    to the length of the Make Note data. This error caused some EXIF 
    display programs to fail to display make note fields created by 
    EXIFEDIT.
      If you have encountered this problem you can fix the Make Note 
    field by editting the EXIF data again using this new version of 
    EXIFEDIT. 

V1.4

  GENERAL
    1) Added support for Canon ShowerShot Maker Note
    2) Minor corrections to field definitions in Casio Maker Note
    3) Removed most warnings when compiling on VC6

  EXIFLIST
    1) Maximum display size for field values increases from 300 to 
       500 bytes
    2) Long field values now wrap at a punctuation mark instead of 
       at maximum line length. 

V1.3

  GENERAL
    1) First production release of EXIFEDIT (a Beta release was included in 
       EXIFutils V1.2)

  EXIFLIST
    1) Now supports Nikon E990 Maker Note.
    2) The meaning of the "file-name" psuedo-field has been changed. In 
       previous versions it contained the full file path and file name.
       It now contains only the file name. To get the path see the 
       file-name-full and file-dir psuedo-fields described below. 
    3) Two new pseudo-fields have been added:
       file-name-full - the full path and file name to the file.
       file-dir       - the directory in which the file was found.
    4) Support for the following fields added:
       - image-history
       - artist
       These are both string fields. 
       NOTE: I'm not sure how "standard" these fields are. The EXIF 
             documentation lists them under "miscellaneous fields".
             If the display program you are using does not support these
             fields then it should ignore them.

  EXIFEDIT
    4) Support for the following fields added:
       - image-history
       - artist
       These are both string fields. 
       NOTE: I'm not sure how "standard" these fields are. The EXIF 
             documentation lists them under "miscellaneous fields".
             If the display program you are using does not support these
             fields then it should ignore them.

V1.2

  GENERAL
    1) Windows version now built using Visual C++ V6.0
    2) Added EXIFEDIT utility, which adds and removes EXIF fields.

  EXIFLIST
    1) Now supports text fields up to 200 characters long (previously
       truncated text fields to 52 characters).
    2) Added support for ASCII User Comment fields (previously the 
       User Comment was displayed as an unformatted hexadecimal string).
    3) Added support for the following new fields:
         - sub-time 
         - sub-time-orig
         - sub-time-digi
         - cfa-pattern
    4) Added support for the Maker Note field for the following cameras:
         - Olympus D-450Z and C-920Z 
         - Nikon E700, E800, E900, E900S, E910, E950
         - Casio QV-2000UX and QV-8000SX
       Note that the meaning of some fields is now known. For these 
       fields the value is display in raw format (see /u option on
       exiflist command).

V1.1.1

  1) No functional changes. Source code changed so that the Windows 
     version will compile using Visual C++ V6.0 (previously Windows
     version was built using GCC).

V1.1

  GENERAL
    1) Added EXIFCOPY utility, which copies EXIF data from one JPEG file
       to another.
    2) Fixed bug in JPEG/EXIF file decoding routines, which could have 
       caused buffer overruns

  EXIFLIST
    1) Add "/f @file-name" option, which allows the list of display fields
       to be read from a text file. This is used to avoid command line 
       length limitations which occur when large numbers of fields are
       specified.

    2) Added defintions for additional Interoperability field, and 
       changed the following field nicknames:
       r98-index -> iop-index
       r98-ver   -> iop-ver 

V1.0

  EXIFDATE
    1) Fix bug which caused exifdate to incorrectly change date fields
       in JPEG files that have been manipulated with 'JPEG Wizard', 
       'ThumbsPlus', 'ACDsee', or any other image manipulation 
       utility that does not save the EXIF Marker as the first marker 
       in the file.

V0.5

  GENERAL
    1) Fix field value display routines to remove redundant blank that was
       present after some values
    2) Fix bug in JPEG file decode routines, which did not correctly handle
       very short files.
    3) Improve robustness of EXIF decode routines so that they are more
       tolerant of badly formatted EXIF fields.  
    4) Tidied up sources to be more compatible with VC++ (renamed ERROR
       #define to EXIF_ERR, and renamed WINDOWS #define to WIN32)

  EXIFLIST
    1) Added /f option to allow user to select which fields get displayed
       in list mode and sql mode.
    2) Added file size (in bytes) as a field value that can be displayed
       in list mode and sql modes.
    3) Add '/o lh' option to allow the user to select whether a heading row
       is printed in list mode ('/o lh' is equivalent to '/o l' in V0.4)
    4) Add /l option to provide list of valid field names that can be 
       specified in the /f option.
    5) Added /v option to show program version information

  EXIFDATE
    1) Added /v option to show program version information 


V0.4

  1) Fix bug which caused exiflist to erroneously report that there was no EXIF
     data in a JPEG file. This occured when the image had been manipulated
     with 'JPEG Wizard', 'ThumbsPlus', or any other image manipulation 
     utility that did no save the EXIF Marker as the first marker in the
     file.

V0.3

  The following changes have been made since V0.2

  FUNCTIONAL CHANGES
  1) the exifdate utility has been added.
  2) the following changes have been made to exiflist
     a. the option to output EXIF fields as an SQL INSERT statement
     b. some command options have been changed to be more extensible,
        and be more consistent with UNIX conventions:
        - the '/l' option has been changed to '/o l'
        - the '/s' option has been changed to '/r'
        - the '/r' option has been changed to '/u'

  SOURCE CODE CHANGES
  The source code has been completely reorganised since V0.2. It has gone
  from a single C file into multiple .c and .h files. The structure of a lot
  of routines has been tied up and it now compiles cleanly without any warnings
  with the Gnu C -Wall option set. 

  There are a lot more comments in the code, but there are still more to be
  done.
