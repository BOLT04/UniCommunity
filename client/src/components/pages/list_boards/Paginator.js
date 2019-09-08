import React from 'react'

import { Pagination } from 'semantic-ui-react'

export default ({ body, onClick }) => {
    const { links } = body

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

                    onClick(url)
                }}
                pointing
                secondary
                totalPages={body.totalPages}
            />
        </div>
    )
}