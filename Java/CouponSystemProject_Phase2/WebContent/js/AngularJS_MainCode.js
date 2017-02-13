var adminApp = angular.module("adminApp", []);

//Company section code

adminApp
		.controller(
				"getAllCompCtrl",
				function($scope, $http) {
					$http
							.get("rest/adminService/getAllCompanies")
							.success(function(response) {
								$scope.companies = response.company;
							})
							.error(
									function(err) {
										window.location
												.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
									});

					$scope.updateComp = function(compFullInf) {
						$http.get(
								"rest/adminService/updateCompany/"
										+ compFullInf.id + "/"
										+ compFullInf.compName + "/"
										+ compFullInf.email).success(
								function(response) {
									alert("UPDATED");
								});
					};

					$scope.showFullCompInf = function(value) {
						$http.get("rest/adminService/getCompany/" + value.id)
								.success(function(response) {
									$scope.compFullInf = response;
									$scope.showSuccessAlert = true;
									// switch flag
									$scope.switchBool = function(value) {
										$scope[value] = !$scope[value];
									};
								});
					}
				})

adminApp.controller("createComp", function($scope, $http) {
	$scope.createCompany = function() {

		var data = $scope.company;
		$http.put("rest/adminService/createCompany", data).then(function() {
			$scope.successTextAlert = "New Company Was Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
		}, function() {
			$scope.errorTextAlert = "Company Creation Failed";
			$scope.showErrorAlert = true;
			// switch flag
			$scope.switchBoolError = function(value) {
				$scope[value] = !$scope[value];
			};
		}

		);
	}
})

adminApp.controller("checkedRemoveCompCtrl", [
		'$scope',
		'$http',
		'filterFilter',
		function checkedRemoveCompCtrl($scope, $http, filterFilter) {

			// selected companies
			$scope.selection = [];

			// helper method to get selected company
			$scope.selectedCompanies = function selectedCompanies() {
				return filterFilter($scope.companies, {
					selected : true
				});
			};

			// watch companies for changes
			$scope.$watch('companies|filter:{selected:true}', function(nv) {
				$scope.selection = nv.map(function(value) {
					return value.id;
				});
			}, true);

			$scope.removeCompFromCheckedList = function() {
				for (var i = 0; i < $scope.selection.length; i++) {
					$http.get(
							"rest/adminService/removeCompany/"
									+ $scope.selection[i]).success(
							function() {
								$http.get("rest/adminService/getAllCompanies")
										.success(function(response) {
											$scope.companies = response.company
										});
							});
				}
			}

		} ]);

adminApp.controller("removeCompSectionCtrl", function($scope, $http) {
	$scope.createCompany = function() {
		var data = $scope.company;
		$http.put("rest/adminService/createCompany", data).success(function() {
			$scope.successTextAlert = "New Company Was Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
		});
	}
})


//Customer section code

adminApp
		.controller(
				"getAllCustomerCtrl",
				function($scope, $http) {
					$http
							.get("rest/adminService/getAllCustomers")
							.success(function(response) {
								$scope.customers = response.customer;
							})
							.error(
									function(err) {
										window.location
												.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
									});

					$scope.updateCustomer = function(customerFullInf) {
						$http.get(
								"rest/adminService/updateCustomer/"
										+ customerFullInf.id + "/"
										+ customerFullInf.custName).success(
								function(response) {
									alert("UPDATED");
								});
					};

					$scope.showFullCustomerInf = function(value) {
						$http.get("rest/adminService/getCustomer/" + value.id)
								.success(function(response) {
									$scope.customerFullInf = response;
									$scope.showSuccessAlert = true;
									// switch flag
									$scope.switchBool = function(value) {
										$scope[value] = !$scope[value];
									};
								});
					}
				})

adminApp.controller("createCustomer", function($scope, $http) {
	$scope.createCustomer = function() {

		var data = $scope.customer;
		$http.put("rest/adminService/createCustomer", data).then(function() {
			$scope.successTextAlert = "New Customer Was Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
		}, function() {
			$scope.errorTextAlert = "Customer Creation Failed";
			$scope.showErrorAlert = true;
			// switch flag
			$scope.switchBoolError = function(value) {
				$scope[value] = !$scope[value];
			};
		}

		);
	}
})

adminApp.controller("checkedRemoveCustomerCtrl", [
		'$scope',
		'$http',
		'filterFilter',
		function checkedRemoveCustomerCtrl($scope, $http, filterFilter) {

			// selected companies
			$scope.selection = [];

			// helper method to get selected company
			$scope.selectedCustomers = function selectedCustomers() {
				return filterFilter($scope.customers, {
					selected : true
				});
			};

			// watch companies for changes
			$scope.$watch('customers|filter:{selected:true}', function(nv) {
				$scope.selection = nv.map(function(value) {
					return value.id;
				});
			}, true);

			$scope.removeCustomersFromCheckedList = function() {
				for (var i = 0; i < $scope.selection.length; i++) {
					$http.get(
							"rest/adminService/removeCustomer/"
									+ $scope.selection[i]).success(
							function() {
								$http.get("rest/adminService/getAllCustomers")
										.success(function(response) {
											$scope.customers = response.customer
										});
							});
				}
			}

		} ]);

adminApp.controller("removeCustomerSectionCtrl", function($scope, $http) {
	$scope.createCustomer = function() {
		var data = $scope.customer;
		$http.put("rest/adminService/createCustomer", data).success(function() {
			$scope.successTextAlert = "New Customer Was Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
		});
	}
})