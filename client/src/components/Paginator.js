import React from 'react'

import { Pagination } from 'semantic-ui-react'

//TODO: finish this! 1. Check the links prop that comes in the rsp from server. 2.adapt this code to that
export default ({ body, onClick }) => {
    const { links } = body
    debugger
    return (
        <div>
            <Pagination
                defaultActivePage={1}
                firstItem={{
                    'aria-label': 'First item',
                    content: 'first',
                    onClick: () => onClick(body.getHrefOfRel('first')),
                    disabled: links && !body.hasLinkRel('first')
                }}
                lastItem={{
                    'aria-label': 'Last item',
                    content: 'last',
                    onClick: () => onClick(body.getHrefOfRel('last')),
                    disabled: links && !body.hasLinkRel('last')
                }}
                nextItem={{
                    'aria-label': 'Last item',
                    content: '⟩',
                    onClick: () => onClick(body.getHrefOfRel('next')),
                    disabled: links && !body.hasLinkRel('next')
                }}
                prevItem={{
                    'aria-label': 'Previous item',
                    content: '⟨',
                    onClick: () => onClick(body.getHrefOfRel('prev')),
                    disabled: links && !body.hasLinkRel('prev')
                }}
                onPageChange={(event, data) => {
                    const params = [
                        { name: 'page', value: data.activePage }
                    ]
                    const url = body.buildUriFromQueryRel('self', params)
                    debugger
                    onClick(url)
                }}
                pointing
                secondary
                totalPages={body.totalPages}
            />
            {/*
        <button disabled={!parsed || !parsed.first}
            onClick={() => onClick(parsed.first.url)} >first</button>
        <button disabled={!parsed || !parsed.prev}
            onClick={() => onClick(parsed.prev.url)} >previous</button>
        <button disabled={!parsed || !parsed.next}
            onClick={() => onClick(parsed.next.url)} >next</button>
            */}</div>
    )
}