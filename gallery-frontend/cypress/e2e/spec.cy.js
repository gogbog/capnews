describe('Login Test', () => {
    beforeEach(() => {
        cy.kcLogin('gogbog', '123456');
    });


    it('should render logged user name somewhere on the page', () => {
        cy.visit('http://localhost:3000/');
        cy.get('.Header_heading-header-title__vvzsD')
            .should('contain', 'Georgiddas Dimitrovdsad');
    });
});

