const express = require('express')
const router = express.Router()
const pool = require('./../pg')

router.get('/', (req, res, next) => {
    pool.query('SELECT * FROM proteges',
    (error, results) => {
        if(error) {throw error}
        res.status(200).json(results.rows)
    })
})

router.get('/:id', (req, res, next) => {
    const id = parseInt(req.params.id)
	
    pool.query('SELECT * FROM proteges WHERE protege_patron = $1', [id], (error, results) => {
        if(error) { throw error }
        res.status(200).json(results.rows)
    })
})

router.get('/id/:id', (req, res, next) => {
    const id = parseInt(req.params.id)
	
    pool.query('SELECT * FROM proteges WHERE protege_id = $1', [id], (error, results) => {
        if(error) { throw error }
        res.status(200).json(results.rows)
    })
})

module.exports = router