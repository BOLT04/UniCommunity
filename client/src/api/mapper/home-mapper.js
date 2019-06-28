
import relsRegistery from '../../common/rels-registery'

export default async function asyncRspToHome(rsp) {
    const contentType = rsp.headers.get('Content-Type')

    if (contentType.includes('application/hal+json')) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { _links, _embedded } = await rsp.json()

        const home = {}
        if (_links) {
          Object
            .keys(_links)
            .forEach(prop => {
              const reg = relsRegistery[prop]
  
              if (reg)
                home[reg.propName] = _links[prop].href
            })
        }

        if (_embedded) {
            Object
                .keys(_embedded)
                .forEach(prop => {
                    const resourceObjectArr = _embedded[prop]
                    if (resourceObjectArr.length == 0) return;

                    const { _links } = resourceObjectArr[0]
                    if (_links && _links.self)
                        home[prop] = _links.self.href
                })
        }
debugger
        return home
    }
}