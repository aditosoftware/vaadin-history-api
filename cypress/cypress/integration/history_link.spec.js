describe('Standalone History Link Component', () => {
    beforeEach(() => {
        cy.visit("/")
    })

    it('should change state correctly', () => {
        cy.get('#test-history-link-1').first().click()
        cy.location('pathname').should('eq', '/client')

        // Assert that it was a PUSH.
        cy.go(-1)
        cy.location('pathname').should('eq', '/')
    })

    it ('element should obtain focus correctly', () => {
        cy.get('#test-history-link-1').first().click()
        cy.get('#test-history-link-1').should("be.focused")
    })

    it ('element should be able to receive focus on external event', () => {
        cy.get('#test-focus-button-1').first().click()

        cy.get('#test-history-link-1').should("be.focused")
    })
})
