define(
		[ "backbone" ],
		function(Backbone, BackboneLocalStorage) {
			var crossper = crossper || {};

			crossper.BusinessInfo = Backbone.Model
					.extend({

						url : function () { return '/crossper/businesses/business/' + this.id; } ,
						
						confirmPassword:'',
						

						validUSStates :[{value:"AK",tokens:["ALASKA"]},{value:"AL",tokens:["ALABAMA","AL"]},{value:"AR",tokens:["ARKANSAS","AR"]},
										{value:"AZ",tokens:["ARIZONA","AZ"]},{value:"CA",tokens:["CALIFORNIA","CA"]},{value:"CO",tokens:["COLORADO","CO"]},
										{value:"CT",tokens:["CONNECTICUT","CT"]},{value:"CZ",tokens:["CANAL ZONE","CZ"]},
										{value:"DC",tokens:["DISTRICT OF COLUMBIA","DC"]},{value:"DE",tokens:["DELAWARE","DE"]},{value:"FL",tokens:["FLORIDA","FL"]},
										{value:"GA",tokens:["GEORGIA","GA"]},{value:"HI",tokens:["HAWAII","HI"]},{value:"IA",tokens:["IOWA","IA"]},
										{value:"ID",tokens:["IDAHO","ID"]},{value:"IL",tokens:["ILLINOIS","IL"]},{value:"IN",tokens:["INDIANA","IN"]},{value:"KS",tokens:["KANSAS","KS"]},
										{value:"KY",tokens:["KENTUCKY","KY"]},{value:"LA",tokens:["LOUISIANA","LA"]},{value:"MA",tokens:["MASSACHUSETTS","MA"]},
										{value:"MD",tokens:["MARYLAND","MD"]},{value:"ME",tokens:["MAINE","ME"]},{value:"MI",tokens:["MICHIGAN","MI"]},
										{value:"MN",tokens:["MINNESOTA","MN"]},{value:"MO",tokens:["MISSOURI","MO"]},{value:"MS",tokens:["MISSISSIPPI","MS"]},
										{value:"MT",tokens:["MONTANA","MT"]},{value:"NE",tokens:["NEBRASKA","NE","NB"]},{value:"NC",tokens:["NORTH CAROLINA","NC"]},
										{value:"ND",tokens:["NORTH DAKOTA","ND"]},{value:"NH",tokens:["NEW HAMPSHIRE","NH"]},{value:"NJ",tokens:["NEW JERSEY","NJ"]},
										{value:"NM",tokens:["NEW MEXICO","NM"]},{value:"NY",tokens:["NEW YORK","NY"]},{value:"NV",tokens:["NEVADA","NV"]},{value:"OH",tokens:["OHIO","OH"]},
										{value:"OK",tokens:["OKLAHOMA","OK"]},{value:"OR",tokens:["OREGON","OR"]},{value:"PA",tokens:["PENNSYLVANIA","PA"]},{value:"PR",tokens:["PUERTO RICO","PR"]},
										{value:"RI",tokens:["RHODE ISLAND","RI"]},{value:"SC",tokens:["SOUTH CAROLINA","SC"]},{value:"SD",tokens:["SOUTH DAKOTA","SD"]},
										{value:"TN",tokens:["TENNESSEE","TN"]},{value:"TX",tokens:["TEXAS","TX"]},{value:"UT",tokens:["UTAH","UT"]},{value:"VA",tokens:["VIRGINIA","VA"]},
										{value:"VI",tokens:["VIRGIN ISLANDS","VI"]},{value:"VT",tokens:["VERMONT","VT"]},{value:"WA",tokens:["WASHINGTON","WA"]},
										{value:"WI",tokens:["WISCONSIN","WI"]},{value:"WV",tokens:["WEST VIRGINIA","WV"]},{value:"WY",tokens:["WYOMING","WY"]}],

						defaults : function() {
							return {
								id:null,
								name : '',
								address : '',
                                                                city: '',
								state : '',
								zipCode : '',
								category : '',
								subCategory : '',
								email : '',
								phone : '',
								website : '',
								averagePrice : '',
								password:'',
								offerQuota : ''

							};
						},

						validate : function(attrs) {
							var errors = [];
							var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
							var url_regex = /^((http|https|ftp|ftps):\/\/)?([a-z0-9\-]+\.)?[a-z0-9\-]+\.[a-z0-9]{2,4}(\.[a-z0-9]{2,4})?(\/.*)?$/i;
							var number_regex=/^\s*\d+\s*$/;
							var phone_regex = /^(\(\d{3}\)|\d{3})(-?| ?)\d{3}(-?| ?)\d{4}$/;
							var zip_regex =  /(^\d{5}$)|(^\d{5}-\d{4}$)/;

							if (!attrs.name.trim()) {
								errors
										.push({
											name : 'name',
											message : 'Please enter a complete title for your business.'
										});
							}

							if (!attrs.state.trim()) {
								errors.push({
									name : 'state',
									message : 'Please fill state field.'
								});
							}else{
								var filteredObj=this.validUSStates.filter(function (state) { return state.value == attrs.state });
								if(filteredObj.length <= 0){
									errors.push({
										name : 'state',
										message : 'State must be valid US state.'
									});
								}
							}
							
                            if (!attrs.city.trim()) {
								errors.push({
									name : 'city',
									message : 'Please fill city field.'
								});
							}
							if (!attrs.zipCode.trim()) {
								errors.push({
									name : 'zipCode',
									message : 'Please fill zipCode field.'
								});
							}else if (!zip_regex.test(attrs.zipCode.trim())) {
								errors.push({
									name : 'zipCode',
									message : 'zipCode is not valid'
								});
							}

							if (attrs.category == "Select Category") {
								errors.push({
									name : 'category',
									message : 'Please select Category.'
								});
							}

							if (attrs.subCategory == "Select SubCategory") {
								errors.push({
									name : 'subCategory',
									message : 'Please select Subcategory.'
								});
							}

							if (attrs.website.trim()) {
                                                            if (!url_regex.test(attrs.website.trim())) {
								errors.push({
									name : 'website',
									message : 'Website is not valid'
								});
                                                            }
                                                        }
							if (!attrs.averagePrice.trim()) {
								errors.push({
									name : 'averagePrice',
									message : 'Please fill Average Sales Price.'
								});
							}else if (!number_regex.test(attrs.averagePrice.trim())) {
								errors.push({
									name : 'averagePrice',
									message : 'AveragePrice is not valid'
								});
							}
							
							if (!attrs.email.trim()) {
								errors.push({
									name : 'email',
									message : 'Please fill email field.'
								});
							} else if (!email_regex.test(attrs.email)) {
								errors.push({
									name : 'email',
									message : 'Email is not valid'
								});
							}

							if (!attrs.phone) {
								errors.push({
									name : 'phone',
									message : 'Please fill phone field.'
								});
							}else if (attrs.phone.trim().match(phone_regex)==null) {
								errors.push({
									name : 'phone',
									message : 'Phone Number is not valid'
								});
							}

							if (!attrs.password) {
								errors.push({
									name : 'password',
									message : 'Please fill password field.'
								});
							}else if(attrs.password.length<6){
									errors.push({
										name : 'password',
										message : 'Password should be at least 6 characters.'
									});
							}else{
								if (!attrs.confirmPassword) {
									errors.push({
										name : 'confirmPassword',
										message : 'Please re-enter your password.'
									});
								}else if(attrs.confirmPassword !=attrs.password){
									errors.push({
										name : 'confirmPassword',
										message : 'Passwords do not match.'
									});
								}
								
							}
							
							return errors.length > 0 ? errors : false;
						},
						
						   parse: function(response){
					            var parsedObject={};
					            
					            parsedObject.name = response.name;
					            parsedObject.locations=response.locations;
					            parsedObject.category = response.category;
					            parsedObject.subCategory = response.subCategory;
					            parsedObject.email = response.email;
					            parsedObject.phone = response.contactPhone;
					            parsedObject.website = response.website;
					            parsedObject.averagePrice = response.averagePrice;
					            parsedObject.offerQuota = response.offerQuota;
					           
					            return parsedObject;
					        }

					});
			return crossper.BusinessInfo;
		});
