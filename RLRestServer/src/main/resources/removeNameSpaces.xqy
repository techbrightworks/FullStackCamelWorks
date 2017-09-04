declare function local:remove-namespaces($element as element()) as element() {
     element { local-name($element) } {
         for $att in $element/@*
         return
             attribute {local-name($att)} {$att},
         for $child in $element/node()
         return
             if ($child instance of element())
             then local:remove-namespaces($child)
             else $child
         }
};

let $check := (//*[local-name() = 'fireworks'])
let $request :=   if(empty ($check))
              then 
               (//*[local-name() = 'firework'])
              else
              (//*[local-name() = 'fireworks'])
 
let $result := (local:remove-namespaces($request))
return $result
