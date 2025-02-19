import ChipFilter from '@/components/common/ChipFilter/ChipFilter';
import * as S from '@/components/common/SearchFilter/SearchFilter.style';

type Props = {
  title: string;
  value: string;
  setValue: React.Dispatch<React.SetStateAction<string>>;
  options: Record<string, string>;
};

function SearchFilter({ title, value, setValue, options }: Props) {
  const handleFilterClick: React.MouseEventHandler<HTMLButtonElement> = ({ target }) => {
    if (!(target instanceof HTMLButtonElement)) return;
    if (target.value === value) {
      setValue(null);
      return;
    }
    setValue(target.value);
  };

  return (
    <S.Container>
      <S.Wrapper>
        <S.FilterTitle>{title}</S.FilterTitle>
        {Object.entries(options).map(([key, content], index: number) => {
          return (
            <ChipFilter
              key={index}
              fontSize={14}
              value={key}
              filter={value}
              handleClick={handleFilterClick}
            >
              {content}
            </ChipFilter>
          );
        })}
      </S.Wrapper>
    </S.Container>
  );
}

export default SearchFilter;
