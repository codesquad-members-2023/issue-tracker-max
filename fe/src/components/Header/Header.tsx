import { css } from '@emotion/react';
import Logo from '../../assets/Logo/Logo';
import UserImageIcon from '../../assets/Icons/UserImageIcon';

export default function Header() {
  return (
    <header css={headerWrapper}>
      <Logo theme="light" size="medium"></Logo>
      <UserImageIcon size="large"></UserImageIcon>
    </header>
  );
}

const headerWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1280px;
  height: 94px;
  margin: 0 auto;
  padding: 0 40px;
`;
