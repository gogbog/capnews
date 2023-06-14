describe('Login Test', () => {
    beforeEach(() => {
        cy.kcLogin('gogbog', '123456');
    });

    it('should login', () => {
        cy.kcLogin('gogbog', '123456');
    });

    it('should render logged user name somewhere on the page', () => {
        cy.visit('http://localhost:3000/');
        cy.get('.Button_button__A5gFc').first().click();


        cy.origin('http://20.106.67.156:8080', () => {

            // Fill the username field
            cy.get('#username').type('gogbog');

            // Fill the password field
            cy.get('#password').type('123456');

            // Submit the form
            cy.get('#kc-login').click();
        });

        cy.get('.Header_heading-header-title__vvzsD')
            .should('contain', 'Georgi Dimitrov');
    });


});

