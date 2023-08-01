import { styled } from 'styled-components';
import DropdownIndicator from './DropdownIndicator';
import Icons from '../../design/Icons';

const StyledDropdownIndicator = styled(DropdownIndicator)`
  height: 40px;
  padding: 0 24px;
  background: ${({ theme }) => theme.color.neutral.surface.default};
  border-right: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium} 0 0
    ${({ theme }) => theme.objectStyles.radius.medium};
`;

export default function FilterBar() {
  const SearchIcon = Icons['search'];

  return (
    <Bar>
      <StyledDropdownIndicator text="Button" />
      <TextFilter>
        <SearchIcon />
        <TextInput></TextInput>
      </TextFilter>
    </Bar>
  );
}

const Bar = styled.div`
  width: 560px;
  display: flex;
  align-items: center;
  border: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  background: ${({ theme }) => theme.color.neutral.surface.bold};

  &:focus-within {
    border-color: ${({ theme }) => theme.color.neutral.border.active};
    background: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const TextFilter = styled.form`
  height: 40px;
  padding: 0 24px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  flex: 1 0 0;
`;

const TextInput = styled.input`
  width: 100%;
  border: none;
  outline: none;
  background: transparent;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]};

  &:focus {
    color: ${({ theme }) => theme.color.neutral.text.default};
  }
`;
