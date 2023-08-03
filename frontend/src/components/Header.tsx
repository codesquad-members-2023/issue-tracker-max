import {useNavigate} from "react-router-dom";
import {styled} from "styled-components";
import {Icon} from "./Icon";

export function Header() {
    const navigate = useNavigate();

    return (
        <Anchor onClick={() => navigate("/")}>
            <Icon name="logoLightMedium"/>
            <img
                style={{width: "32px"}}
                src="https://avatars.githubusercontent.com/u/41321198?v=4"
            />
        </Anchor>
    );
}

const Anchor = styled.a`
  width: 1280px;
  display: inline-flex;
  padding: 27px 80px;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
`;
