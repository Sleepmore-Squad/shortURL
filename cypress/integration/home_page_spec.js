

describe('The Home Page', function() {
    it('successfully loads', function() {
        // eslint-disable-next-line no-undef
        cy.visit('/'); // change URL to match your dev URL
        // eslint-disable-next-line no-undef
        cy.get('.input > .ant-input').type("dxy1");
        // eslint-disable-next-line no-undef
        cy.get('.inputpassword > .ant-input').type("123456");
        // eslint-disable-next-line no-undef
        cy.get('.login1').click();
        // eslint-disable-next-line no-undef
        cy.url().should('eq', 'http://localhost:3000/home');
        cy.get('.ant-input').type("12345");
        cy.get('.ant-btn > span').click();
        cy.get('.ant-input').eq('');
        cy.wait(2000);

        cy.get('.ant-input').type("https://baudu.com");
        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'q');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.get('text').contains('短链接：'+'r');

        cy.get('.ant-btn > span').click();
        cy.wait(2000);
        cy.get('text').contains('短链接：');

    })
})