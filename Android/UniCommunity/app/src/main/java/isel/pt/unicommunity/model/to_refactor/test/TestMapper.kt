package isel.pt.unicommunity.model.to_refactor.test

import isel.pt.unicommunity.model.to_refactor.mapper.IMapper

class TestMapper : IMapper<TestDto,TestModel> {

    override fun dtoToModel(dto: TestDto)=
            TestModel(dto.name)
}