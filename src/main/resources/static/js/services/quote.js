'use strict'

angular.module('app.services', ['ngResource'])

.factory('QuoteService', function($resource) {
	return $resource('/api/quote/:id', {id:'@_id'}, {
		random: {
			method: 'GET',
			url: '/api/quote/random'
		}
	
	});
})

.factory('Quotelist', function($resource) {
	return $resource('/api/quote/:id', {id:'@_id'}, {
		list: {
			method: 'GET', isArray:true,
			url: '/api/quote/list'
			
		}
	
	});
})

.factory('DetailQuote', function($resource) {
	return $resource('/api/quote/:id', {id:'@_id'}, {
		detail: {
			method: 'GET', isArray:true,
			url: '/api/quote/detail',
		}
	
	});
});

