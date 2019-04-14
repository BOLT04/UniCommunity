import HomeApi from './HomeApi'

const body = Promise.resolve({
    _links: {
        self: { href: '/home' }, 
        '/rels/nav': { href: '/' },
        '/rels/feed': { href: '/feed' }
    },
    _embedded: {
        'http://localhost:8080/rels/feed': [
            {
                name: 'Anuncio 1',
                shortDesc: 'Avaliação das Resoluções das Séries de Exercícios e Notas Finais da Turma LI51D',
                author: 'Carlos Martins',
                _links: {
                    self: { href: '/boards/DAW-1819-v/announcements/1' },
                    '/rels/userProfile': { href: '/users/101/profile' }
                }
            },
            {
                name: 'Recurso: Lab 5',
                author: 'José Simão',
                _links: {
                    self: { href: '/boards/DAW-1819-v/announcements/1' },
                    '/rels/userProfile': { href: '/users/101/profile' }
                }
            }
        ]
    }
})

export default class HomeApiMock extends HomeApi {

    fetchHome = () => {
        const rsp = new Response()
        rsp.json = () => body
        rsp.headers.append('Content-Type', 'application/hal+json')
        
        return rsp
    }
}
