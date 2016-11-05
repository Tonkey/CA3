
    // Include Modules
    //beforeEach(module('myApp'));
    //beforeEach(module('myApp.angular-jwt'));
    //beforeEach(module('myApp.security'));
    //beforeEach(module('ui.router'));

    // Suite for testing an individual piece of our feature.
    describe('Test CVR service', function () {
        
        beforeEach(angular.mock.module('myApp.services'));
        
        var CVRService;

        // Inject dependencies
        beforeEach(inject(function (_CVRService_) {
            CVRService = _CVRService_;
        }));

        // It block (or "spec") to test expectations for the
        // Expectations return true or false.
        it('test', function () {
           //????
          //  CVRService.search("MAERSK","DK",callback,failure);
            expect(2+2).toEqual(4);
            
            
        });
    });
    