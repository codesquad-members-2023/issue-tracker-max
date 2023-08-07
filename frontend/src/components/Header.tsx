import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Icon } from "./icon/Icon";

export function Header() {
  const navigate = useNavigate();

  return (
    <Div>
      <Anchor onClick={() => navigate("/")}>
        <Icon name="LogoMedium" color="neutralTextStrong" />
      </Anchor>
      <img
        style={{ width: "32px" }}
        src="https://avatars.githubusercontent.com/u/41321198?v=4"
      />
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: inline-flex;
  padding: 27px 80px;
  justify-content: space-between;
  align-items: center;
`;

const Anchor = styled.a`
  cursor: pointer;
`;
