import styled from 'styled-components';

import { useThemeMode } from 'contexts/ThemeModeContext';

export const Header = () => {
  const { toggleTheme } = useThemeMode();

  return (
    <div>
      <StyledTest>테스트</StyledTest>

      <button onClick={toggleTheme}>Toggle Theme</button>
    </div>
  );
};

const StyledTest = styled.p`
  font: ${({ theme: { font } }) => font.displayB32};
  color: ${({ theme: { color } }) => color.textWeak};
`;
