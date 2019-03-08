/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

var
    // Main
    gulp = require('gulp'),
    concat = require('gulp-concat'),
    // Config
    resourceDirectory = 'src/main/webapp/resources/',
    jsDirectory = resourceDirectory + 'gjs/*.js',
    sassDirectory = [
        resourceDirectory + 'scss/assets/*.scss',
        resourceDirectory + 'scss/*.scss'
    ],
    // Js
    uglify = require('gulp-uglify'),
    // Css
    sass = require('gulp-sass'),
    autoprefixer = require('autoprefixer'),
    postcss = require('gulp-postcss')
;

gulp.task('scripts', function () {

    gulp.src(jsDirectory)
        .pipe(concat('script.js'))
        //.pipe(uglify())
        .pipe(gulp.dest(resourceDirectory + 'js'));

});

gulp.task('style', function () {
    gulp.src(sassDirectory)
        .pipe(concat('style.scss'))
        .pipe(sass().on('error', sass.logError))
        .pipe(postcss([autoprefixer({ remove:false })]))
        .pipe(gulp.dest(resourceDirectory + 'css'));
});

gulp.task('default', function () {
    gulp.watch(sassDirectory, ['style']);
    gulp.watch(jsDirectory, ['scripts']);
});