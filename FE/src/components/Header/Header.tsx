import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";

type Props = {
  toggleTheme(): void;
  profileImg: string;
};

export default function Header({ toggleTheme, profileImg }: Props) {
  return (
    <Wrapper>
      <Container>
        <Logo href={"/issues/isOpen=true"}>
          <LogoImg src={"/logo/mediumLogo.svg"} alt={"Issue Tracker"} />
        </Logo>
        <UserProfileButton
          src={profileImg}
          size={"large"}
          onClick={toggleTheme}
        />
      </Container>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Container = styled.header`
  padding: 0px 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-width: 1440px;
  height: 94px;
`;

const Logo = styled.a`
  width: 200px;
  height: 40px;
`;

const LogoImg = styled.img`
  filter: ${({ theme }) => theme.filter.neutral.text.strong};
`;
