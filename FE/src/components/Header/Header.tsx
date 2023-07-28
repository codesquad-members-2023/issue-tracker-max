import { styled } from "styled-components";

type Props = {
  toggleTheme(): void;
};

export default function Header({ toggleTheme }: Props) {
  return (
    <Wrapper>
      <Logo href={"/"}>
        <LogoImg src={"/logo/mediumLogo.svg"} alt={"Issue Tracker"} />
      </Logo>
      <button onClick={toggleTheme}>다크모드</button>
      <ProfileButton>
        <ProfileImg src={"/logo/profile.jpg"} />
      </ProfileButton>
    </Wrapper>
  );
}

const Wrapper = styled.header`
  padding: 0px 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1440px;
  height: 94px;
`;

const Logo = styled.a`
  width: 200px;
  height: 40px;
`;

const LogoImg = styled.img`
  filter: ${({ theme }) => theme.filter.neutral.text.strong};
`;

const ProfileButton = styled.button`
  width: 32px;
  height: 32px;
`;

const ProfileImg = styled.img`
  width: 32px;
  height: 32px;
  border-radius: ${({ theme }) => theme.radius.half};
`;
