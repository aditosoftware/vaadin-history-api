describe('History API', () => {
    beforeEach(() => {
        // Always reset the state.
        cy.visit('/')
    })

    it('should push new state correctly', () => {
        cy.get("#test-mod-button-push-1").first().click()
        cy.location("pathname").should("match", /\/push\/(\d+)/gm)
        cy.go(-1)
        cy.location("pathname").should("eq", "/")
    })

    it('should replace current state correctly', () => {
        cy.get("#test-mod-button-replace-1").first().click()
        cy.location("pathname").should("match", /\/replace\/(\d+)/gm)
    })

    it('should go back correctly', () => {
        cy.get('#test-mod-button-push-1').first().click()
        cy.get('#test-mod-button-back-1').first().click()

        cy.location("pathname").should("eq", "/")
    })

    it ('should go forward correctly', () => {
        cy.get('#test-mod-button-push-1').first().click()
        cy.location("pathname").should("match", /\/push\/(\d+)/gm)
        cy.go(-1)

        cy.get('#test-mod-button-forward-1').first().click()
        cy.location("pathname").should("match", /\/push\/(\d+)/gm)
    })
})
