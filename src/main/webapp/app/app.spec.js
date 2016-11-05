describe('Unit: App', function () {

    // Include Modules
    beforeEach(module('myApp'));
    beforeEach(module('myApp.angular-jwt'));
    beforeEach(module('myApp.security'));
    beforeEach(module('ui.router'));

    // Suite for testing an individual piece of our feature.
    describe('Test View1', function () {
        
        beforeEach(module('myApp.view1'));

        // Instantiate global variables (global to all tests in this describe block).
        var $state,
            $rootScope,
            state = 'app';

        // Inject dependencies
        beforeEach(inject(function (_$state_, _$rootScope_) {
            $state = _$state_;
            $rootScope = _$rootScope_;
        }));

        // It block (or "spec") to test expectations for the
        // Expectations return true or false.
        it('verifies state configuration', function () {
            var config = $state.get(state);
            expect(config.abstract).toBeTruthy();
            expect(config.url).toBeUndefined();
        });
    });
    
    
});