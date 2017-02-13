var companyApp = angular.module("companyApp", []);

companyApp
		.controller(
				"getAllCoupCtrl",
				function($scope, $http) {
					$http
							.get("rest/companyService/getAllCoupons")
							.success(function(response) {
								$scope.coupons = response.coupon;
							})
							.error(
									function(err) {
										window.location
												.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
									});
					$scope.updateCoup = function(coupFullInf) {
						$http.get(
								"rest/companyService/updateCoupon/"
										+ coupFullInf.id + "/"
										+ coupFullInf.endDate + "/"
										+ coupFullInf.price).success(
								function(response) {
									alert("UPDATED");
								});

						$scope.showFullCoupInf = function(value) {
							$http
									.get(
											"rest/companyService/getCoupon/"
													+ value.id)
									.success(function(response) {
										$scope.coupFullInf = response;
										$scope.showSuccessAlert = true;
										// switch flag
										$scope.switchBool = function(value) {
											$scope[value] = !$scope[value]
										};
									});
						}
					}
				})

companyApp
		.controller(
				"getCoupByTypeCtrl",
				function($scope, $http) {
					$scope.getCouponsByPrice = function() {

						$http
								.get(
										"rest/companyService/getCouponsByType/"
												+ $scope.coupon.type)
								.success(function(response) {
									$scope.coupons = response.coupon;
								})
								.error(
										function(err) {
											window.location
													.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
										});
						$scope.updateCoup = function(coupFullInf) {
							$http.get(
									"rest/companyService/updateCoupon/"
											+ coupFullInf.id + "/"
											+ coupFullInf.endDate + "/"
											+ coupFullInf.price).success(
									function(response) {
										alert("UPDATED");
									});

							$scope.showFullCoupInf = function(value) {
								$http
										.get(
												"rest/companyService/getCoupon/"
														+ value.id)
										.success(
												function(response) {
													$scope.coupFullInf = response;
													$scope.showSuccessAlert = true;
													// switch flag
													$scope.switchBool = function(
															value) {
														$scope[value] = !$scope[value]
													};
												});
							}
						}
					}
				})

companyApp
		.controller(
				"getCoupByPriceCtrl",
				function($scope, $http) {
					$http
							.get(
									"rest/companyService/getCouponsByPrice/"
											+ $scope.coupon.price)
							.success(function(response) {
								$scope.coupons = response.coupon;
							})
							.error(
									function(err) {
										window.location
												.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
									});
					$scope.updateCoup = function(coupFullInf) {
						$http.get(
								"rest/companyService/updateCoupon/"
										+ coupFullInf.id + "/"
										+ coupFullInf.endDate + "/"
										+ coupFullInf.price).success(
								function(response) {
									alert("UPDATED");
								});

						$scope.showFullCoupInf = function(value) {
							$http
									.get(
											"rest/companyService/getCoupon/"
													+ value.id)
									.success(function(response) {
										$scope.coupFullInf = response;
										$scope.showSuccessAlert = true;
										// switch flag
										$scope.switchBool = function(value) {
											$scope[value] = !$scope[value]
										};
									});
						}
					}
				})

companyApp
		.controller(
				"getCoupByDateCtrl",
				function($scope, $http) {
					$http
							.get(
									"rest/companyService/getCouponsByDate/"
											+ $scope.coupon.date)
							.success(function(response) {
								$scope.coupons = response.coupon;
							})
							.error(
									function(err) {
										window.location
												.assign("http://localhost:8080/CouponSystemProject_State02/LoginPage.html");
									});
					$scope.updateCoup = function(coupFullInf) {
						$http.get(
								"rest/companyService/updateCoupon/"
										+ coupFullInf.id + "/"
										+ coupFullInf.endDate + "/"
										+ coupFullInf.price).success(
								function(response) {
									alert("UPDATED");
								});

						$scope.showFullCoupInf = function(value) {
							$http
									.get(
											"rest/companyService/getCoupon/"
													+ value.id)
									.success(function(response) {
										$scope.coupFullInf = response;
										$scope.showSuccessAlert = true;
										// switch flag
										$scope.switchBool = function(value) {
											$scope[value] = !$scope[value]
										};
									});
						}
					}
				})

companyApp.controller("createCoup", function($scope, $http) {
	$scope.createCoupon = function() {

		var data = $scope.coupon;
		$http.put("rest/companyService/addCoupon", data).then(function() {
			$scope.successTextAlert = "New Coupon Has Been Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
		}, function() {
			$scope.errorTextAlert = "Coupon Creation Failed";
			$scope.showErrorAlert = true;
			// switch flag
			$scope.switchBoolError = function(value) {
				$scope[value] = !$scope[value];
			};

		})
	}
})

companyApp.controller("checkedRemoveCoupCtrl", [
		'$scope',
		'$http',
		'filterFilter',
		function checkedRemoveCoupCtrl($scope, $http, filterFilter) {
			// selected coupons
			$scope.selection = [];

			// helper method to get selected coupon
			$scope.selectedCoupons = function selectedCoupons() {
				return filterFilter($scope.coupons, {
					selected : true
				})
			}

			// watch coupons for changes
			$scope.$watch('coupons|filter:{selected:true}', function(nv) {
				$scope.selection = nv.map(function(value) {
					return value.id;
				})
			}, true);
			$scope.removeCoupFromCheckedList = function() {
				for (var i = 0; i < $scope.selection.length; i++) {
					$http.get(
							"rest/companyService/removeCoupon"
									+ $scope.selection[i]).success(
							function() {
								$http.get("rest/companyService/getAllCoupons")
										.success(function(response) {
											$scope.coupons = resopnse.coupon
										})
							})
				}
			}
		} ])

companyApp.controller("removeCoupSectionCtrl", function($scope, $http) {
	$scope.createCoupon = function() {
		var data = $scope.coupon;
		$http.put("rest/companyService/addCoupon", data).success(function() {
			$scope.successTextAlert = "New Coupon Has Been Created";
			$scope.showSuccessAlert = true;
			// switch flag
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			}
		})
	}
})