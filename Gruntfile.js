module.exports = function(grunt){
  grunt.initConfig({
	concat: {
	  options: {
	     separator: ';' 	
	   },
	  foo: {
	     options: { separator: ''},
	     src:['js/first.js','js/second.js'],
	     dest:'js/foo.js'
	   },
	  bar: { }    			
	 },
	shell:{
	    server:{
		  command: 'java -cp L1.2-1.0-jar-with-dependencies.jar main.Main 8080'
		}
	  },
	fest:{
		templates: {
			files: [{
				expand: true,
				cwd: 'templates',
				src: '*.xml',
				dest: 'public_html/js/tmpl'
			}],
			options: {
				template: function (data) {
					return grunt.template.process(
						'var <%= name %>Tmpl = <%= contents %> ;',
						{data: data}
					);
				}
			}
		}
	},
	any_other_name:'hello'
});
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-fest');
  grunt.loadNpmTasks('grunt-shell');
  grunt.registerTask('default',['concat']);
};

