#!/bin/bash
     # rename files to lower/upper case...
     #
     # usage:
     #    move-to-lower *
     #    move-to-upper *
     # or
     #    move-to-lower -R .
     #    move-to-upper -R .
     #
     
     echo filnavn $0
     
     help()
{
             cat << eof
     Usage: $0 [-n] [-r] [-h] files...
     
     -n      do nothing, only see what would be done
     -R      recursive (use find)
     -h      this message
     files   files to remap to lower case
     
     Examples:
            $0 -n *        (see if everything is ok, then...)
            $0 *
     
            $0 -R .
     
eof
}
     
     apply_cmd='sh'
     finder='echo "$@" | tr " " "\n"'
     files_only=
     
     while :
     do
         case "$1" in
             -n) apply_cmd='cat' ;;
             -R) finder='find "$@" -type f';;
             -h) help ; exit 1 ;;
             *) break ;;
         esac
         shift
     done
     
     if [ -z "$1" ]; then
             echo Usage: $0 [-h] [-n] [-r] files...
             exit 1
     fi
     
     LOWER='abcdefghijklmnopqrstuvwxyz'
     UPPER='ABCDEFGHIJKLMNOPQRSTUVWXYZ'
     
     case `basename $0` in
             *upper*) TO=$UPPER; FROM=$LOWER ;;
             *)       FROM=$UPPER; TO=$LOWER ;;
     esac
     
     eval $finder | sed -n '
     
     # remove all trailing slashes
     s/\/*$//
     
     # add ./ if there is no path, only a filename
     /\//! s/^/.\//
     
     # save path+filename
     h
     
     # remove path
     s/.*\///
     
     # do conversion only on filename
     y/'$FROM'/'$TO'/
     
     # now line contains original path+file, while
     # hold space contains the new filename
     x
     
     # add converted file name to line, which now contains
     # path/file-name\nconverted-file-name
     G
     
     # check if converted file name is equal to original file name,
     # if it is, do not print nothing
     /^.*\/\(.*\)\n\1/b
     
     # now, transform path/fromfile\n, into
     # mv path/fromfile path/tofile and print it
     s/^\(.*\/\)\(.*\)\n\(.*\)$/mv "\1\2" "\1\3"/p
     
     ' | $apply_cmd

RETUR=$?

exit $RETUR
