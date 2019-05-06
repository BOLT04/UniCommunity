package isel.pt.unicommunity.model.webmapper

interface IMapper<DTO, M> {
    fun dtoToModel(dto: DTO):M
}